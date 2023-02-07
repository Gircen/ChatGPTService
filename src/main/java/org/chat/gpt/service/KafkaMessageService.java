package org.chat.gpt.service;

import org.chat.gpt.dto.MessageKafkaDto;

public interface KafkaMessageService {

    MessageKafkaDto save(MessageKafkaDto dto);

    void send(MessageKafkaDto dto);

    void consume(MessageKafkaDto dto);
}
