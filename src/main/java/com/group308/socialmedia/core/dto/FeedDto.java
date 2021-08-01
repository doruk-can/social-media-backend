package com.group308.socialmedia.core.dto;


import com.group308.socialmedia.core.dto.common.SuperDto;
import com.group308.socialmedia.core.model.domain.PostInteraction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FeedDto extends SuperDto {

    private long postId;

    private String postOwnerName;

    private Date postDate;

    private String postText;

    private String postImage;

    private String postTopic;

    private String postVideoURL;

    private String postGeoId;

    private String postGeoName;

    private long totalPostLike;

    private long totalPostDislike;

    private List<PostCommentDto> postCommentDto;

    private boolean userLikedIt;

    private boolean userDislikedIt;


}


