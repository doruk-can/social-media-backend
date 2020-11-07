package com.group308.socialmedia.core.security.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by isaozturk on 19.11.2018
 */

@Data
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String header;

    private String secret;

    private Long expiration;

    private String authServerName;
}
