package com.rafamilo.itunesws.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import javax.annotation.PostConstruct;

public class RabbitContainerSingleton {

    public static GenericContainer<?> RABBIT_CONTAINER;
    private static String USERNAME;
    private static String PASSWORD;
    private static String PORT;
    private static String IMAGE_NAME;
    private static String IMAGE_VERSION;

    @PostConstruct
    public static void RabbitSingleton() {
        if (RABBIT_CONTAINER == null) {
            RABBIT_CONTAINER = new GenericContainer<>(getRabbitMqImageName()).withEnv("RABBITMQ_DEFAULT_USER", USERNAME)
                    .withEnv("RABBITMQ_DEFAULT_PASS", PASSWORD)
                    .withExposedPorts(Integer.parseInt(PORT));

            RABBIT_CONTAINER.setPortBindings(ImmutableList.of("0.0.0.0:" + PORT + ":" + PORT));

            RABBIT_CONTAINER.start();
        }
    }

    private static String getRabbitMqImageName() {
        return IMAGE_NAME + ":" + IMAGE_VERSION;
    }

    @Value("${spring.rabbitmq.username}")
    private void setUsername(final String username) {
        USERNAME = username;
    }

    @Value("${spring.rabbitmq.password}")
    private void setPassword(final String password) {
        PASSWORD = password;
    }

    @Value("${spring.rabbitmq.port}")
    private void setPort(final String port) {
        PORT = port;
    }

    @Value("${itunesws.properties.utils.rabbitmq.image.name}")
    private void setImageName(final String imageName) {
        IMAGE_NAME = imageName;
    }

    @Value("${itunesws.properties.utils.rabbitmq.image.version}")
    private void setImageVersion(final String imageVersion) {
        IMAGE_VERSION = imageVersion;
    }
}