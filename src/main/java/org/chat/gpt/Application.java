package org.chat.gpt;


import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

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

@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@EnableKafka
@EntityScan("org.chat.gpt.database.entity")
@EnableJpaAuditing
@EnableJpaRepositories("org.chat.gpt.database.dao.repository")
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
