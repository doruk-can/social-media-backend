package com.group308.socialmedia;

import com.group308.socialmedia.core.model.service.ApplicationUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*@Slf4j
@Component
public class SchedulingConfiguration {


    @Autowired
    private ApplicationUserService applicationUserService;
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Scheduled(fixedDelay = 10000)
    public void run() {




                executorService.submit(new FlyingCallable(play));


            });
        }
    }
}*/









/*package com.group308.socialmedia;

import com.group308.socialmedia.core.model.service.ApplicationUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {


    static ApplicationUserService applicationUserService;
    @Scheduled(fixedDelay = 10000L)
    static void someJob() throws InterruptedException {
        if(applicationUserService.findByUsername("harun").getUsername().equals("Harun")) {
            System.out.println("afdsagfdsgdsfgdfsg  " + new Date()); //deneme
        }
    }

}*/

