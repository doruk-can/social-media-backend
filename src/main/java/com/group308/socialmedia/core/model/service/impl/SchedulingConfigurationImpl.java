package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.service.ApplicationUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@AllArgsConstructor
public class SchedulingConfigurationImpl {

    private final ApplicationUserService applicationUserService;
    @Scheduled(fixedDelay = 10000000L)
    void someJob() throws ParseException {

        List<ApplicationUser> applicationUserList = applicationUserService.findAllByActiveIsFalse();

        Date date = new Date();
        String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date currentTime=formatter.parse(str);

        for(int i=0; i< applicationUserList.size(); i++) {
            if(applicationUserList.get(i).getDeactivatedUntil().before(currentTime)) {

                ApplicationUser appUser = applicationUserService.findByUsername(applicationUserList.get(i).getUsername());
                applicationUserService.deleteById(appUser.getId());
                appUser.setActive(true);
                applicationUserService.save(appUser);
                System.out.println("User " + applicationUserList.get(i).getUsername() + " is activated!"  );
            }
        }


    }
}