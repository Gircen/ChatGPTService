package org.chat.gpt;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

import java.io.IOException;


@ComponentScan(basePackages = {
        "org.chat.gpt",
        "org.chat.gpt.config",
        "org.chat.gpt.controller",
        "org.chat.gpt.dto",
        "org.chat.gpt.factory",
        "org.chat.gpt.factory.common",
        "org.chat.gpt.service"
})
@EnableDiscoveryClient
@SpringBootApplication
@EnableKafka
@EnableJpaAuditing
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}