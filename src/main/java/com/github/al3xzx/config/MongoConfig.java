package com.github.al3xzx.config;

import lombok.Builder;
import lombok.Data;

import java.util.Properties;

@Data
@Builder
public class MongoConfig {

    private String username;
    private String password;
    private String host;
    private String port;
    private String database;
    private String collection;

    public static MongoConfig loadProps(Properties properties) {
        return MongoConfig.builder()
                .username(properties.getProperty("mongodb.username"))
                .password(properties.getProperty("mongodb.password"))
                .host(properties.getProperty("mongodb.host"))
                .port(properties.getProperty("mongodb.port"))
                .database(properties.getProperty("mongodb.database"))
                .collection(properties.getProperty("mongodb.collection"))
                .build();
    }
}
