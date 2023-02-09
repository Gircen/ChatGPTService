package org.chat.gpt.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageKafkaDto extends AbstractDto {
    private Long id;
    private String value;
}
