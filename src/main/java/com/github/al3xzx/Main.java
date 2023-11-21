package com.github.al3xzx;

import com.github.al3xzx.config.AppConfig;
import com.github.al3xzx.connector.MqttToMongoDBConnector;


public class Main {
    public static void main(String[] args) {
        new MqttToMongoDBConnector(AppConfig.getInstance());
    }
}