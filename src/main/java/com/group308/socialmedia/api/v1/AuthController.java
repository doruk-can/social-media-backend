package com.group308.socialmedia.api.v1;

import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.dto.user.AppUserDto;
import com.group308.socialmedia.core.dto.user.JwtUserDto;
import com.group308.socialmedia.core.exception.ResourceNotFoundException;
import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.service.ApplicationUserService;
import com.group308.socialmedia.core.security.JwtTokenHelper;
import com.group308.socialmedia.core.security.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;

    private final ApplicationUserService applicationUserService;

    private final PasswordService passwordService;

    private final JwtTokenHelper jwtTokenHelper;

    private final Environment environment;


    public AuthController(@Qualifier("UserDetailsServiceImpl") UserDetailsService userDetailsService,
                          ApplicationUserService applicationUserService,
                          PasswordService passwordService,
                          JwtTokenHelper jwtTokenHelper,
                          Environment environment) {

        this.userDetailsService = userDetailsService;
        this.applicationUserService = applicationUserService;
        this.passwordService = passwordService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.environment = environment;
    }


    @PostMapping("/login")
    public ResponseEntity<RestResponse<JwtUserDto>> login(@RequestBody AppUserDto request) {

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        final String password = passwordService.decryptPassword(userDetails.getPassword());

        if (!password.equals(request.getPassword())) {
            throw new ResourceNotFoundException("NotFound.general.message",request.getUsername());
        }

        if (password.equals(request.getPassword())) {

            final ApplicationUser user  =applicationUserService.findByUsername(userDetails.getUsername());

            final String token = jwtTokenHelper.generateToken(user.getUsername(),user.getId());

            String version = environment.getProperty("group308.version");

            final JwtUserDto jwtUserResource = new JwtUserDto(user.getUsername(),token,userDetails.getAuthorities(),version);


            return new ResponseEntity<>(RestResponse.of(jwtUserResource, Status.SUCCESS,""), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();

    }
}

