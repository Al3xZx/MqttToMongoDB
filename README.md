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

## Cleanup

To stop and remove the Docker containers, use the following command:

```bash
docker-compose down
```

Feel free to explore the application's source code and configurations for further customization.

Happy coding! 🚀

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


