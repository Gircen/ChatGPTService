package org.chat.gpt.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
public class KafkaConfig {

    @Value("${kafka.kafka.server}")
    private String kafkaServer;

    @Value("${kafka.kafka.group.id}")
    private String kafkaGroupId;

    @Value("${kafka.kafka.producer.id}")
    private String kafkaProducerId;

    @Value("${kafka.kafka.consumer.id}")
    private String kafkaConsumerId;

}
