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
@Table(name = "POST_INTERACTIONS")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenericGenerator(name = "TABLE_SEQUENCE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "none"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM
                        , value = "post_interaction_seq")
        }
)
public class PostInteraction extends AbstractEntity {
        @Column(name="post_id")
        private long postId;

        @Column(name = "interaction_type")
        private String interactionType;

        @Column(name = "post_like")
        private long postLike;

        @Column(name = "post_dislike")
        private long postDislike;

        @Column(name = "post_comment")
        private String postComment;

        @Column(name = "commentator_id")
        private long commentatorId;

}
