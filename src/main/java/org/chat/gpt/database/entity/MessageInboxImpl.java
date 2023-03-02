package org.chat.gpt.database.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message_inbox")
public class MessageInboxImpl extends Message{

    @Column(columnDefinition = "uuid")
    UUID uuid;

    @Column(columnDefinition = "varchar")
    String value;
}


