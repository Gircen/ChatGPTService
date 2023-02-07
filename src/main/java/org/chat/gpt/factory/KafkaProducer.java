package org.chat.gpt.factory;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.chat.gpt.config.KafkaConfig;
import org.chat.gpt.dto.MessageKafkaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducer {

    private final KafkaConfig config;

    public KafkaProducer(KafkaConfig config) {
        this.config = config;
    }


    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getKafkaServer());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, config.getKafkaProducerId());
        return props;
    }

    @Bean
    public ProducerFactory<Long, MessageKafkaDto> producerMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, MessageKafkaDto> kafkaTemplate() {
        KafkaTemplate<Long, MessageKafkaDto> template = new KafkaTemplate<>(producerMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
