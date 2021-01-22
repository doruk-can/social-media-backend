package com.group308.socialmedia.core.dto;

import com.group308.socialmedia.core.dto.common.SuperDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionalMessageDto extends SuperDto{

    private String messageFrom;
    private String messageTo;
    private String messageDate;
    private String messageContent;

}
