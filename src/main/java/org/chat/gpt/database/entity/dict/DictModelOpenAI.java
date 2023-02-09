package org.chat.gpt.database.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "dict_model_open_ai")
public class DictModelOpenAI extends AbstractDict{

    @Column(columnDefinition = "varchar", nullable = false)
    String name;

    @Column(columnDefinition = "varchar")
    String sname;

    @Column(columnDefinition = "varchar", nullable = false)
    String value;

    @Column(columnDefinition = "varchar",  nullable = false)
    String description;

}
