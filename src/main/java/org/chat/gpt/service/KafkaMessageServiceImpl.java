package org.chat.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.chat.gpt.dto.MessageKafkaDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaMessageServiceImpl implements KafkaMessageService{

    private final KafkaTemplate<Long, MessageKafkaDto> kafkaMessageTemplate;
    private final ObjectMapper objectMapper;

    public KafkaMessageServiceImpl(KafkaTemplate<Long, MessageKafkaDto> kafkaMessageTemplate, ObjectMapper objectMapper) {
        this.kafkaMessageTemplate = kafkaMessageTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public MessageKafkaDto save(MessageKafkaDto dto) {
        return null;
    }

    @Override
    public void send(MessageKafkaDto dto) {
        log.info("<= sending {}", writeValueAsString(dto));
        kafkaMessageTemplate.send("server.chatdpt", dto);
    }

    @Override
    @KafkaListener(id = "ChatDPT", topics = {"server.chatdpt"}, containerFactory = "singleFactory")
    public void consume(MessageKafkaDto dto) {
        log.info("=> consumed {}", writeValueAsString(dto));
    }

    private String writeValueAsString(MessageKafkaDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
