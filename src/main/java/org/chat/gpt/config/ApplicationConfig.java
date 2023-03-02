package org.chat.gpt.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Data
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
