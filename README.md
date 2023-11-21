# MqttToMongoDB Dockerized Application

This repository contains a Dockerized Java application that connects to an MQTT broker, subscribes to a specified topic,
and stores the received messages in a MongoDB database.

## Prerequisites

Before running the application, make sure you have Docker installed on your machine. You can download and install Docker
from [the official Docker website](https://www.docker.com/get-started).

## Usage

To test the application, follow these steps:

1. Clone this repository to your local machine:

    ```bash
    git clone https://github.com/Al3xZx/MqttToMongoDB.git
    ```

2. Navigate to the `docker` directory:

    ```bash
    cd MqttToMongoDB/docker
    ```

3. Run the application using Docker Compose:

    ```bash
    docker-compose up -d
    ```

   This command will build and start the Docker containers defined in the `docker-compose.yml` file.

4. To send data to the MQTT topic, you can use an MQTT client. One such client is "MQTTX" (https://mqttx.app/) or "MQTT
   Explorer" (https://mqtt-explorer.com/). Connect to the MQTT broker with the following details:

    - **Broker Address:** localhost
    - **Port:** 1883
    - **Username:** mqttuser
    - **Password:** mqttpsw
    - **Topic:** topic/test

   Publish messages to this topic to observe the application's behavior.

Feel free to explore the application's source code and configurations for further customization.

Happy coding! 🚀

## Cleanup

To stop and remove the Docker containers, use the following command:

```bash
docker-compose down
```

## Environment Variables

The MqttToMongoDB application uses several environment variables for configuration. When running the application using
Docker Compose, you can customize these variables in the `docker-compose.yml` file.

### MongoDB Configuration

- **MONGO_DB_USERNAME:** The username for MongoDB authentication. Default: `root`.
- **MONGO_DB_PASSWORD:** The password for MongoDB authentication. Default: `passw0rd!`. **Please exercise caution before
  changing the default password for security reasons.**

- **MONGO_DB_HOST:** The hostname or IP address of the MongoDB server. Default: `localhost`.
- **MONGO_DB_PORT:** The port number on which MongoDB is running. Default: `27017`.
- **MONGO_DB_DATABASE:** The name of the MongoDB database. Default: `mqtt`.
- **MONGO_DB_COLLECTION:** The name of the MongoDB collection where data will be stored. Default: `mqttData`.

### MQTT Configuration

- **MQTT_USERNAME:** The username for connecting to the MQTT broker. Default: `mqttuser`.
- **MQTT_PASSWORD:** The password for connecting to the MQTT broker. Default: `mqttpsw`. **Changing the default MQTT
  password is recommended for security reasons.**

- **MQTT_HOST:** The hostname or IP address of the MQTT broker. Default: `localhost`.
- **MQTT_PORT:** The port number on which the MQTT broker is running. Default: `1883`.
- **MQTT_CLIENT_NAME:** The name of the MQTT client. Default: `mqtt-mongo-connector`.
- **MQTT_TOPIC:** The MQTT topic to which the application subscribes for incoming messages.
  Default: `$share/group/topic/test/#`.

Feel free to modify these variables according to your specific environment and requirements.

## MQTT Shared Subscriptions and Wildcards

The MqttToMongoDB Connector utilizes features in MQTT related to Shared Subscriptions and topic wildcards. Understanding
these concepts is crucial for configuring the connector to work effectively with your MQTT broker.

### Shared Subscriptions - `$share/group`

MQTT Shared Subscriptions allow multiple clients to share the same subscription and collectively receive messages sent
to that subscription. In the configuration, you'll notice the use of `$share/group` in the `MQTT_TOPIC` variable.

Here's how it works:

- **`$share`:** This is a special topic level used for Shared Subscriptions.

- **`group`:** This represents the Shared Subscription group name. Multiple clients subscribing to the same topic with
  the same group name form a group that collectively receives messages sent to that topic.

Here are the advantages:

- **Load Distribution:** Shared Subscriptions enable the distribution of message processing load among multiple
  instances of the MqttToMongoDB Connector. This is particularly beneficial when you have multiple instances running,
  and you want to distribute the load evenly.

- **High Availability:** In the case of one instance going offline, the other instances in the Shared Subscription group
  can continue processing messages. This enhances the availability and fault tolerance of your MqttToMongoDB connector.

- **Scalability:** As your application grows, you can scale horizontally by adding more instances of the MqttToMongoDB
  Connector. Shared Subscriptions allow these instances to work together seamlessly without duplication of messages.

### Multi-level Wildcard - `#`

The `#` symbol in MQTT is a multi-level wildcard that represents multiple levels in the topic hierarchy.
In the configuration, `#` is used to subscribe to all subtopics under the Shared Subscription group.

### Example:

Suppose you have a topic `test/data`, and multiple clients need to collectively process messages sent to this topic.
Instead of each client subscribing to `test/data`, they can use Shared Subscriptions:

```markdown
MQTT_TOPIC: $share/group/test/# 
```
