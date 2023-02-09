package org.chat.gpt.database.entity;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "message_inbox")
public class MessageInboxImpl extends Message{

    @Column(columnDefinition = "uuid")
    UUID uuid;

    @Column(columnDefinition = "varchar")
    String value;
}


