package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.*;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.OptionalMessage;
import com.group308.socialmedia.core.model.domain.OptionalNotification;
import com.group308.socialmedia.core.model.service.OptionalMessageService;
import com.group308.socialmedia.core.model.service.OptionalNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/optchat")
public class OptionalMessageController {

    private final OptionalMessageService optionalMessageService;
    private final OptionalNotificationService optionalNotificationService;

    @PostMapping("/send/{username}/{secondUsername}")
    public ResponseEntity<RestResponse<String>> sendPrivateMessage(@PathVariable("username") String username,
                                                            @PathVariable("secondUsername") String secondUsername,
                                                            @RequestBody OptionalMessageDto optionalMessageDto){

        OptionalMessage optionalMessage = new OptionalMessage();

        optionalMessage.setMessageTo(optionalMessageDto.getMessageTo());
        optionalMessage.setMessageFrom(optionalMessageDto.getMessageFrom());
        optionalMessage.setMessageContent(optionalMessageDto.getMessageContent());

        Date date = new Date();
        String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        optionalMessage.setMessageDate(str);
        System.out.println(date);
        System.out.println(str);
        //optionalMessage.setMessageDate(optionalMessageDto.getMessageDate());

        //optional notification
        OptionalNotification optionalNotification = new OptionalNotification();
        optionalNotification.setNotificationContent(optionalMessageDto.getMessageFrom() + " sent you a message: " + optionalMessageDto.getMessageContent());
        optionalNotification.setNotificationFrom(optionalMessageDto.getMessageFrom());
        optionalNotification.setNotificationTo(optionalMessageDto.getMessageTo());
        optionalNotification.setNotificationDate(str);
        optionalNotification.setIsSent(0);
        optionalNotificationService.save(optionalNotification);
        //



        optionalMessageService.save(optionalMessage);

        return new ResponseEntity<>(RestResponse.of("Message is sent", Status.SUCCESS,""), HttpStatus.OK);
    }


        @GetMapping("/{username}/{secondUsername}")
    public ResponseEntity<RestResponse<List<OptionalMessageDto>>> getMessages(@PathVariable("username") String username,
                                                                              @PathVariable("secondUsername") String secondUsername) {

        List<OptionalMessage> optionalMessageList1 = optionalMessageService.findAllByMessageFromAndMessageTo(username, secondUsername);
        List<OptionalMessage> optionalMessageList2 = optionalMessageService.findAllByMessageFromAndMessageTo(secondUsername, username);


        Set<OptionalMessageDto> returnedMessageSet = new HashSet<>();

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        for(int i=0; i<optionalMessageList1.size(); i++) {
            OptionalMessageDto optionalMessageDto = new OptionalMessageDto();
            optionalMessageDto.setMessageFrom(optionalMessageList1.get(i).getMessageFrom());
            optionalMessageDto.setMessageTo(optionalMessageList1.get(i).getMessageTo());
            optionalMessageDto.setMessageContent(optionalMessageList1.get(i).getMessageContent());
            optionalMessageDto.setMessageDate(optionalMessageList1.get(i).getMessageDate());

            returnedMessageSet.add(optionalMessageDto);
        }


        for(int i=0; i<optionalMessageList2.size(); i++) {
            OptionalMessageDto optionalMessageDto = new OptionalMessageDto();
            optionalMessageDto.setMessageFrom(optionalMessageList2.get(i).getMessageFrom());
            optionalMessageDto.setMessageTo(optionalMessageList2.get(i).getMessageTo());
            optionalMessageDto.setMessageContent(optionalMessageList2.get(i).getMessageContent());
            optionalMessageDto.setMessageDate(optionalMessageList2.get(i).getMessageDate());

            returnedMessageSet.add(optionalMessageDto);
        }

        final List<OptionalMessageDto> optionalMessageDtoList = new ArrayList<>(returnedMessageSet);

        optionalMessageDtoList.sort(Comparator.comparing(OptionalMessageDto::getMessageDate));


        return new ResponseEntity<>(RestResponse.of(optionalMessageDtoList, Status.SUCCESS, ""), HttpStatus.OK);

        }



    @PostMapping("/check/{username}/{secondUsername}")
    public ResponseEntity<RestResponse<OptionalMessageCheckDto>> checkAndGetMessages(@PathVariable("username") String username,
                                                                                      @PathVariable("secondUsername") String secondUsername,
                                                                                      @RequestBody OptionalMessageDto optionalMessageDto3) throws ParseException {

        List<OptionalMessage> optionalMessageList1 = optionalMessageService.findAllByMessageFromAndMessageTo(username, secondUsername);
        List<OptionalMessage> optionalMessageList2 = optionalMessageService.findAllByMessageFromAndMessageTo(secondUsername, username);


        Set<OptionalMessageDto> returnedMessageSet = new HashSet<>();

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date checkingDate=formatter.parse(optionalMessageDto3.getMessageDate());

       // DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        //Date checkingDate = optionalMessageDto3.getMessageDate();
        Date maxDate =new Date(System.currentTimeMillis()-7*24*60*60*1000); // not important some days ago to compare

        for(int i=0; i<optionalMessageList1.size(); i++) {
            OptionalMessageDto optionalMessageDto2 = new OptionalMessageDto();
            optionalMessageDto2.setMessageFrom(optionalMessageList1.get(i).getMessageFrom());
            optionalMessageDto2.setMessageTo(optionalMessageList1.get(i).getMessageTo());
            optionalMessageDto2.setMessageContent(optionalMessageList1.get(i).getMessageContent());
            optionalMessageDto2.setMessageDate(optionalMessageList1.get(i).getMessageDate());

            if(formatter.parse(optionalMessageDto2.getMessageDate()).after(maxDate)){
                maxDate = formatter.parse(optionalMessageDto2.getMessageDate());
            }

            returnedMessageSet.add(optionalMessageDto2);
        }


        for(int i=0; i<optionalMessageList2.size(); i++) {
            OptionalMessageDto optionalMessageDto2 = new OptionalMessageDto();
            optionalMessageDto2.setMessageFrom(optionalMessageList2.get(i).getMessageFrom());
            optionalMessageDto2.setMessageTo(optionalMessageList2.get(i).getMessageTo());
            optionalMessageDto2.setMessageContent(optionalMessageList2.get(i).getMessageContent());
            optionalMessageDto2.setMessageDate(optionalMessageList2.get(i).getMessageDate());

            if(formatter.parse(optionalMessageDto2.getMessageDate()).after(maxDate)){
                maxDate = formatter.parse(optionalMessageDto2.getMessageDate());
            }

            returnedMessageSet.add(optionalMessageDto2);
        }

        OptionalMessageCheckDto optionalMessageCheckDto = new OptionalMessageCheckDto();
        if(maxDate.after(checkingDate)) {
            optionalMessageCheckDto.setOptionalMessageDtoList(new ArrayList<>(returnedMessageSet));
            // List<OptionalMessageDto> optionalMessageDtoList = new ArrayList<>(returnedMessageSet);
            optionalMessageCheckDto.getOptionalMessageDtoList().sort(Comparator.comparing(OptionalMessageDto::getMessageDate));
            optionalMessageCheckDto.setMessageStatus(true);
        } else {
            optionalMessageCheckDto.setMessageStatus(false);
            optionalMessageCheckDto.setOptionalMessageDtoList(new ArrayList<>());
        }



        return new ResponseEntity<>(RestResponse.of(optionalMessageCheckDto, Status.SUCCESS, ""), HttpStatus.OK);

    }


    }
