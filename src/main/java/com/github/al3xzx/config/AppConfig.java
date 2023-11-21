package com.github.al3xzx.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Data
@Slf4j
public class AppConfig {

    private MongoConfig mongoConfig;

    private MqttConfig mqttConfig;

    private static AppConfig instance;

    private AppConfig() {

    }

    public static AppConfig getInstance() {
        return instance == null ? loadProps() : instance;
    }

    private static AppConfig loadProps() {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                String errorMsg = "Unable to find application.properties";
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            Properties properties = new Properties();
            properties.load(input);
            resolveEnvironmentVariables(properties);
            instance = new AppConfig();
            instance.setMongoConfig(MongoConfig.loadProps(properties));
            instance.setMqttConfig(MqttConfig.loadProps(properties));
            return instance;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static void resolveEnvironmentVariables(Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (value != null && value.matches("\\$\\{.*?\\}")) {
                int colonIndex = value.indexOf(':');
                String envVarName = (colonIndex != -1) ? value.substring(2, colonIndex) : value.substring(2, value.length() - 1);
                String defaultValue = (colonIndex != -1) ? value.substring(colonIndex + 1, value.length() - 1) : "";
                String envVarValue = System.getenv().getOrDefault(envVarName, defaultValue);
                properties.setProperty(key, envVarValue);
            }
        }
    }


}
