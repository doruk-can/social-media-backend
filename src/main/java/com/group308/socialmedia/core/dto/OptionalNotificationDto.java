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
public class OptionalNotificationDto extends SuperDto {

    private String notificationFrom;
    private String notificationContent;
    private String notificationDate;

}
