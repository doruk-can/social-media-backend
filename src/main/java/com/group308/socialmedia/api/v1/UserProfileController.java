package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.FeedDto;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.dto.user.AppUserDto;
import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.domain.user.ApplicationUserRoleRelation;
import com.group308.socialmedia.core.model.service.ApplicationUserService;
import com.group308.socialmedia.core.security.PasswordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/profile")
public class UserProfileController {

    private final ApplicationUserService applicationUserService;

    private final PasswordService passwordService;

    @GetMapping("/{username}")
    public ResponseEntity<RestResponse<AppUserDto>> findFeedPosts(@PathVariable("username") String username) {

        ApplicationUser applicationUser = applicationUserService.findByUsername(username);
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setUsername(applicationUser.getUsername());
        appUserDto.setEmail(applicationUser.getEmail());
        appUserDto.setProfilePicture(applicationUser.getProfilePicture());
        appUserDto.setActive(applicationUser.isActive());
        appUserDto.setDeleted(applicationUser.isDeleted());
        Date deactivatedUntil = applicationUser.getDeactivatedUntil();
        appUserDto.setDeactivatedUntil(deactivatedUntil);

        return new ResponseEntity<>(RestResponse.of(appUserDto, Status.SUCCESS, ""), HttpStatus.OK);
    }

    @PutMapping("/{username}/edit")
    public ResponseEntity<RestResponse<String>> editProfile(@PathVariable("username") String username,
                                                            @RequestBody AppUserDto appUserDto) throws IOException {


        final String password = passwordService.encryptPassword(appUserDto.getPassword());

        try {
           // String emailUser = applicationUserService.findByEmailAndUsername(appUserDto.getEmail(), appUserDto.getUsername()).getEmail();
            String email = applicationUserService.findByEmail(appUserDto.getEmail()).getEmail();
            return new ResponseEntity<>(RestResponse.of("User email already exists", Status.SYSTEM_ERROR, ""), HttpStatus.NOT_FOUND);

        } catch(Exception e){
            System.out.println("No error");
        }

        final ApplicationUser applicationUser = applicationUserService.findByUsername(appUserDto.getUsername());

        applicationUser.setId(applicationUserService.findByUsername(username).getId());
        applicationUser.setPassword(password);
        applicationUser.setEmail(appUserDto.getEmail());
        applicationUser.setProfilePicture(appUserDto.getProfilePicture());


        applicationUserService.save(applicationUser);


        return new ResponseEntity<>(RestResponse.of("Profile is updated", Status.SUCCESS,""), HttpStatus.OK);
    }



}
