package org.chat.gpt.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "message_outbox")
public class MessageOutboxImpl extends Message{

    @Column(columnDefinition = "uuid")
    UUID uuid;

    @Column(columnDefinition = "varchar")
    String value;


}
