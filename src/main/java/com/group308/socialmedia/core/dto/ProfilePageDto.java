package com.group308.socialmedia.core.dto;

import com.group308.socialmedia.core.dto.common.SuperDto;
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
public class ProfilePageDto extends SuperDto {

    private String username;

    private String email;

    private String profilePicture;

    private boolean active;

    private Date deactivatedUntil;

    private List<String> followingNamesList;

    private List<String> followerNamesList;

    private List<String> subscribedTopicNamesList;

    private List<String> subscribedLocationIdsList;

    private List<FeedDto> userPostsList;
}
