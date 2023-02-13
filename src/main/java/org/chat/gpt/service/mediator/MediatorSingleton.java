package org.chat.gpt.service.mediator;

import lombok.Data;
import org.chat.gpt.database.dao.repository.MessageRepositoryInbox;
import org.chat.gpt.database.entity.MessageInboxImpl;
import org.chat.gpt.dto.MessageKafkaDto;
import org.chat.gpt.dto.OpenAiRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Configuration
@Data
public class MediatorSingleton {

    private static MessageRepositoryInbox messageRepositoryInbox;
    volatile MediatorSingleton mediatorSingleton;

    @Bean("mediatorSingleton")
    @Scope("singleton")
    public MediatorSingleton getInstance() {
        return new MediatorSingleton();
    }
    @Transactional
    public void start(MessageKafkaDto messageKafkaDto) {
        if(messageKafkaDto.getValue() == null)
            return;
        messageRepositoryInbox.findByUuid(UUID.fromString(messageKafkaDto.getValue()));
    }
    private OpenAiRequest converter(Optional<MessageInboxImpl> messageInbox) {
        return null;
    }
}
