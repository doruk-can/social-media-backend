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

@Slf4j
@Component
public class SchedulingConfigurationImpl {

    ApplicationUserService applicationUserService;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Scheduled(fixedDelay = 2000)
    public void run() {

        Runnable runnableTask = () -> {
            try {
               // TimeUnit.MILLISECONDS.sleep(300);
                    System.out.println("afdsagfdsgdsfgdfsg  " + new Date()); //deneme
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executorService.submit(runnableTask);
    }
}