package com.github.al3xzx.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.al3xzx.config.AppConfig;
import com.github.al3xzx.config.MongoConfig;
import com.github.al3xzx.config.MqttConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Map;
import java.util.UUID;

@Slf4j
public class MqttToMongoDBConnector implements MqttCallback {

    private final MongoCollection<Document> mongoCollection;

    private final AppConfig appConfig;

    public MqttToMongoDBConnector(AppConfig appConfig) {
        this.appConfig = appConfig;
        MongoConfig mongoConfig = appConfig.getMongoConfig();
        String connectionString = String.format("mongodb://%s:%s@%s:%s",
                mongoConfig.getUsername(), mongoConfig.getPassword(), mongoConfig.getHost(), mongoConfig.getPort());
        MongoClient mongoClient = MongoClients.create(connectionString); // ex: "mongodb://root:passw0rd!@localhost:27017"
        MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoConfig.getDatabase());// ex: "mqtt"
        mongoCollection = mongoDatabase.getCollection(mongoConfig.getCollection());// ex: "mqttData"

        // Init MQTT connection
        MqttConfig mqttConfig = appConfig.getMqttConfig();
        String broker = String.format("tcp://%s:%s", mqttConfig.getHost(), mqttConfig.getPort()); // "tcp://localhost:1883"
        String clientName = mqttConfig.getClientName(); // "mqtt-mongo-connector";
        String clientId = clientName + "-" + UUID.randomUUID();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(mqttConfig.getUsername()); //"mqttuser"
            connOpts.setPassword(mqttConfig.getPassword().toCharArray()); // "mqttpsw"
            connOpts.setCleanSession(true);
            log.info("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            log.info("Connected");

            String topic = mqttConfig.getTopic(); //"$share/group/topic/test/#";
            mqttClient.subscribe(topic, 1);

            mqttClient.setCallback(this);

        } catch (MqttException me) {
            log.error(me.getMessage(), me);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("Connection lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String payload = new String(message.getPayload());
            log.info("Received message: {} from topic: {}", payload, topic);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(payload, Map.class);

            Document document = new Document("topic", topic)
                    .append("payload", map)
                    .append("timestamp", System.currentTimeMillis());
            mongoCollection.insertOne(document);

            log.info("Message saved to MongoDB");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
