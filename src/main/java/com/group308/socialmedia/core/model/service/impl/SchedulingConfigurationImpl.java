package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.model.service.ApplicationUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
@Slf4j
@Component
@AllArgsConstructor
public class SchedulingConfigurationImpl {

    private final ApplicationUserService applicationUserService;
    @Scheduled(fixedDelay = 10000L)
    void someJob() {
        if(applicationUserService.findByUsername("harun").getUsername().equals("Harun")) {
            System.out.println("afdsagfdsgdsfgdfsg  " + new Date()); //deneme
        }
        System.out.println("sdafadsfsd");
    }
}*/