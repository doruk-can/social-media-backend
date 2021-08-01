package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.OptionalMessageDto;
import com.group308.socialmedia.core.dto.OptionalNotificationDto;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.OptionalNotification;
import com.group308.socialmedia.core.model.service.OptionalNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/optnotif")
public class OptionalNotificationController {

    private final OptionalNotificationService optionalNotificationService;

    @GetMapping("/allnotifications/{username}")
    public ResponseEntity<RestResponse<List<OptionalNotificationDto>>> getAllNotifications(@PathVariable("username") String username) {

        List<OptionalNotification> optionalNotificationList = optionalNotificationService.findAllByNotificationTo(username);
        List<OptionalNotificationDto> optionalNotificationDtoList = new ArrayList<>();
        for(int i=0; i<optionalNotificationList.size(); i++) {
            OptionalNotificationDto optionalNotificationDto = new OptionalNotificationDto();
            optionalNotificationDto.setNotificationContent(optionalNotificationList.get(i).getNotificationContent());
            optionalNotificationDto.setNotificationDate(optionalNotificationList.get(i).getNotificationDate());
            optionalNotificationDto.setNotificationFrom(optionalNotificationList.get(i).getNotificationFrom());
            optionalNotificationDtoList.add(optionalNotificationDto);
        }

        return new ResponseEntity<>(RestResponse.of(optionalNotificationDtoList, Status.SUCCESS, ""), HttpStatus.OK);
    }

    @GetMapping("/lastnotification/{username}")
    public ResponseEntity<RestResponse<OptionalNotificationDto>> getLastNotification(@PathVariable("username") String username) {
        OptionalNotificationDto optionalNotificationDto = new OptionalNotificationDto();

        try {
            OptionalNotification optionalNotification = optionalNotificationService.findByNotificationToAndIsSent(username, 0);
            optionalNotificationDto.setNotificationFrom(optionalNotification.getNotificationFrom());
            optionalNotificationDto.setNotificationContent(optionalNotification.getNotificationContent());
            optionalNotificationDto.setNotificationDate(optionalNotification.getNotificationDate());

            optionalNotification.setIsSent(1);
            optionalNotificationService.deleteById(optionalNotification.getId());
            optionalNotificationService.save(optionalNotification);
        }
        catch (Exception e) {

        }

        return new ResponseEntity<>(RestResponse.of(optionalNotificationDto, Status.SUCCESS, ""), HttpStatus.OK);
    }


    }
