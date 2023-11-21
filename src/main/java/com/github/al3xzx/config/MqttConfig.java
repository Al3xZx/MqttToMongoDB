package com.github.al3xzx.config;

import lombok.Builder;
import lombok.Data;

import java.util.Properties;

@Data
@Builder
public class MqttConfig {

    private String username;
    private String password;
    private String host;
    private String port;
    private String clientName;
    private String topic;


    public static MqttConfig loadProps(Properties properties) {
        return MqttConfig.builder()
                .username(properties.getProperty("mqtt.username"))
                .password(properties.getProperty("mqtt.password"))
                .host(properties.getProperty("mqtt.host"))
                .port(properties.getProperty("mqtt.port"))
                .clientName(properties.getProperty("mqtt.clientName"))
                .topic(properties.getProperty("mqtt.topic"))
                .build();
    }
}
