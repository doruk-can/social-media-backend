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
public class RequestedUserDto extends SuperDto {

    private long userId;

    private String username;
}
