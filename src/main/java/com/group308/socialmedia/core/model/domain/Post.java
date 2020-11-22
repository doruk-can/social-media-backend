package com.group308.socialmedia.core.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "POSTS")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenericGenerator(name = "TABLE_SEQUENCE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "none"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM
                        , value = "post_seq")
        }
)
public class Post extends AbstractEntity {

    @Column(name="post_owner_name")
    private String postOwnerName;

    @Column(name = "post_image")
    private String postImage;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "post_video_url")
    private String postVideoURL;

    @Column(name = "post_topic")
    private String postTopic;

    @Column(name = "post_geo_id")
    private long postGeoId;

    @Column(name = "post_geo_name")
    private String postGeoName;


}
