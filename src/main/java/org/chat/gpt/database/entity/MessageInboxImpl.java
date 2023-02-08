package org.chat.gpt.database.entity;


import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Entity
@Table(name = "message_inbox", catalog = "chat_gpt")
public class MessageInboxImpl extends Message{

    @Id
    Long id;
    @Column(columnDefinition = "uuid")
    UUID uuid;

    @Column(columnDefinition = "varchar")
    String message;
}


