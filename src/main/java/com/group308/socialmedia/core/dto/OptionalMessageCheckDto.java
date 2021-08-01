package com.group308.socialmedia.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class OptionalMessageCheckDto {


    private List<OptionalMessageDto> optionalMessageDtoList;
    private boolean messageStatus;

}
