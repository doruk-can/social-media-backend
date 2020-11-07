package com.group308.socialmedia.core.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by isaozturk on 1.08.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserDto implements Serializable {

    private  String username;

    private  String token;

    private Collection<? extends GrantedAuthority> authorities;

    private String version;
}
