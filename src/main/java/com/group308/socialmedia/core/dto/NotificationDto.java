package com.group308.socialmedia.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class NotificationDto {

    private String notificationMessage;

}
