package com.group308.socialmedia.core.dto;


import com.group308.socialmedia.core.dto.common.SuperDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FeedDto extends SuperDto {

    private long postId;

    private String postOwnerName;

    private String postText;

    private String postImage;

    private String postTopic;

    private String postVideoURL;

    private long postGeoId;

    private String postGeoName;

    private long totalPostLike;

    private long totalPostDislike;

    private PostInteractionDto postInteractionDto;

}
