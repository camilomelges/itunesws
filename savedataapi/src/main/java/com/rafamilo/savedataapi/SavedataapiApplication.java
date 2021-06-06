package com.rafamilo.savedataapi;

import com.rafamilo.savedataapi.configurations.MongoDB;
import com.rafamilo.savedataapi.configurations.RabbitMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MongoDB.class, RabbitMQ.class})
public class SavedataapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SavedataapiApplication.class, args);
    }

}
