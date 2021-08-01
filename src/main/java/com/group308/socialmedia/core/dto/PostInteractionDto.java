package com.group308.socialmedia.core.dto;


import com.group308.socialmedia.core.dto.common.SuperDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostInteractionDto extends SuperDto {

    private long postId;

    private long postLike;

    private long postDislike;

    private String postComment;

    private String commentatorName;
}
