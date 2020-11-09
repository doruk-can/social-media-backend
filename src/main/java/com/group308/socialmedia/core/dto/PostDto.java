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
public class PostDto extends SuperDto {

    private String postOwnerName;

    private String postText;

    private String postImage;

    private String postTopic;

    private String postVideoURL;

    private long postLocationLatitude;

    private long postLocationLongitude;
}