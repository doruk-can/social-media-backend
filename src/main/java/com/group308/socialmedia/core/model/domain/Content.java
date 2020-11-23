package com.group308.socialmedia.core.model.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "CONTENTS")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenericGenerator(name = "TABLE_SEQUENCE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "none"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM
                        , value = "content_seq")
        }
)
public class Content extends AbstractEntity {

        @Column(name="content_type")
        private String contentType;

        @Column(name = "topic")
        private String topic;

        @Column(name = "geo_Id")
        private String geoId;

}
