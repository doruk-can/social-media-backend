package com.group308.socialmedia.core.model.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "OPTIONAL_MESSAGES")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenericGenerator(name = "TABLE_SEQUENCE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "none"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM
                        , value = "optional_messages_seq")
        }
)
public class OptionalMessage extends AbstractEntity  {

    @Column(name="message_from")
    private String messageFrom;

    @Column(name="message_to")
    private String messageTo;

    @Column(name="message_date")
    private String messageDate;

    @Column(name="message_content")
    private String messageContent;

}
