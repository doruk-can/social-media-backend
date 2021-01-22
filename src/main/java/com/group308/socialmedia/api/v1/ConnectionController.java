package com.group308.socialmedia.api.v1;

import com.group308.socialmedia.core.dto.ConnectionDto;
import com.group308.socialmedia.core.dto.NotificationDto;
import com.group308.socialmedia.core.dto.PostDto;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.Connection;
import com.group308.socialmedia.core.model.domain.OptionalNotification;
import com.group308.socialmedia.core.model.service.ApplicationUserService;
import com.group308.socialmedia.core.model.service.ConnectionService;
import com.group308.socialmedia.core.model.service.OptionalNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/connections")
public class ConnectionController {

private final ConnectionService connectionService;

private final ApplicationUserService applicationUserService;

private final OptionalNotificationService optionalNotificationService;


    @Autowired
    private SimpMessagingTemplate template;


    @PostMapping("/follow")
    public ResponseEntity<RestResponse<String>> followUser(@RequestBody ConnectionDto connectionDto) throws IOException {

        Connection connection = new Connection();
        long followerId = applicationUserService.findByUsername(connectionDto.getFollowerName()).getId();
        long followingId = applicationUserService.findByUsername(connectionDto.getFollowingName()).getId();
        connection.setFollowerId(followerId);
        connection.setFollowingId(followingId);

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationMessage(connectionDto.getFollowerName() + " started to follow you");


        try{
            Connection connectionCheck = connectionService.findByFollowerIdAndFollowingId(followerId, followingId);
            System.out.println("User can't follow same user");
            if(connectionCheck != null) {
                return new ResponseEntity<>(RestResponse.of("User can't follow same user twice", Status.SYSTEM_ERROR,""), HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            connectionService.save(connection);
            template.convertAndSend("/topic/notification/" + connectionDto.getFollowingName(), notificationDto ); // sending notification

            //optional notification
            Date date = new Date();
            String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
            OptionalNotification optionalNotification = new OptionalNotification();
            optionalNotification.setNotificationContent(connectionDto.getFollowerName() + " is started to follow you!");
            optionalNotification.setNotificationFrom(connectionDto.getFollowerName());
            optionalNotification.setNotificationTo(connectionDto.getFollowingName());
            optionalNotification.setNotificationDate(str);
            optionalNotification.setIsSent(0);
            optionalNotificationService.save(optionalNotification);
            //

            return new ResponseEntity<>(RestResponse.of("Following is done", Status.SUCCESS,""), HttpStatus.OK);
        }


        connectionService.save(connection);
        template.convertAndSend("/topic/notification/" + connectionDto.getFollowingName(), notificationDto ); // sending notificaiton

        //optional notification
        Date date = new Date();
        String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        OptionalNotification optionalNotification = new OptionalNotification();
        optionalNotification.setNotificationContent(connectionDto.getFollowerName() + " is started to follow you!");
        optionalNotification.setNotificationFrom(connectionDto.getFollowerName());
        optionalNotification.setNotificationTo(connectionDto.getFollowingName());
        optionalNotification.setNotificationDate(str);
        optionalNotification.setIsSent(0);
        optionalNotificationService.save(optionalNotification);
        //

        return new ResponseEntity<>(RestResponse.of("Following is done", Status.SUCCESS,""), HttpStatus.OK);
    }


}