package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.user.AppUserDto;
import com.group308.socialmedia.core.dto.user.JwtUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/register")
public class RegistrationController {

   // @PostMapping("/")
    //public ResponseEntity<RestResponse<JwtUserDto>> login(@RequestBody AppUserDto request) {
   // }

}
