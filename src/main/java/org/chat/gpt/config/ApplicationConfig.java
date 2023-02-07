package org.chat.gpt.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Getter
@Component
@RefreshScope
public class ApplicationConfig {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String address;

    @Value("${server.address}:${server.port}")
    private String serviceFullAddress;

    @Value("${kafka.kafka.group.id}")
    private String test;


}
