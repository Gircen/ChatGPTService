package org.chat.gpt.database.entity;

import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Entity
@Table(name = "message_outbox", catalog = "chat_gpt")
public class MessageOutboxImpl extends Message{

    @Id
    Long id;

    @Column(columnDefinition = "uuid")
    UUID uuid;
    @Column(columnDefinition = "varchar")
    String message;

    @ManyToOne
    @JoinColumn(name = "message_inbox_id")
    @Column(columnDefinition = "integer")
    MessageInboxImpl inbox;

    @javax.persistence.Id
    public Long getId() {
        return id;
    }
}
