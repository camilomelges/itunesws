package com.rafamilo.savedataapi.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class MongoDBContainerSingleton extends RabbitContainerSingleton {

    public static GenericContainer<?> MONGODB_CONTAINER;
    private static String DATABASE;
    private static String USERNAME;
    private static String PASSWORD;
    private static String PORT;
    private static String IMAGE_NAME;
    private static String IMAGE_VERSION;

    @PostConstruct
    public static void MongoSingleton() {
        if (MONGODB_CONTAINER == null) {

            MONGODB_CONTAINER = new GenericContainer<>(getMongoDBImageName())
                    .withEnv("MONGO_INITDB_ROOT_USERNAME", USERNAME)
                    .withEnv("MONGO_INITDB_ROOT_PASSWORD", PASSWORD)
                    .withEnv("MONGO_INITDB_DATABASE", DATABASE)
                    .withExposedPorts(Integer.parseInt(PORT));

            List<String> list = new ArrayList<>();
            list.add("MONGO_INITDB_ROOT_USERNAME=".concat(USERNAME));
            list.add("MONGO_INITDB_ROOT_PASSWORD=".concat(PASSWORD));
            list.add("MONGO_INITDB_DATABASE=".concat(DATABASE));
            MONGODB_CONTAINER.setEnv(list);
            MONGODB_CONTAINER.withExposedPorts(Integer.parseInt(PORT));
            MONGODB_CONTAINER.setPortBindings(ImmutableList.of("0.0.0.0:" + PORT + ":" + PORT));

            MONGODB_CONTAINER.start();
        }
    }

    private static String getMongoDBImageName() {
        return IMAGE_NAME + ":" + IMAGE_VERSION;
    }

    @Value("${spring.data.mongodb.database}")
    private void setDatabase(final String database) {
        DATABASE = database;
    }

    @Value("${spring.data.mongodb.username}")
    private void setUsername(final String username) {
        USERNAME = username;
    }

    @Value("${spring.data.mongodb.password}")
    private void setPassword(final String password) {
        PASSWORD = password;
    }

    @Value("${spring.data.mongodb.port}")
    private void setPort(final String port) {
        PORT = port;
    }

    @Value("${savedataapi.properties.spring.data.mongodb.image.name}")
    private void setImageName(final String imageName) {
        IMAGE_NAME = imageName;
    }

    @Value("${savedataapi.properties.spring.data.mongodb.image.version}")
    private void setImageVersion(final String imageVersion) {
        IMAGE_VERSION = imageVersion;
    }
}