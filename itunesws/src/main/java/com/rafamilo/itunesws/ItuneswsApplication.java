package com.rafamilo.itunesws;

import com.rafamilo.itunesws.configurations.RabbitMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RabbitMQ.class})
public class ItuneswsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItuneswsApplication.class, args);
    }
}
