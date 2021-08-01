package com.group308.socialmedia.core.dto.user;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by isaozturk on 1.08.2020
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto implements Serializable {

    private String username;

    private String password;

    private String email;

    private String profilePicture;

    private boolean deleted;

    private boolean active;

    private Date deactivatedUntil;
}
