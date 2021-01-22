package com.group308.socialmedia.core.model.service.impl;


import com.group308.socialmedia.core.model.domain.OptionalMessage;
import com.group308.socialmedia.core.model.domain.OptionalNotification;
import com.group308.socialmedia.core.model.repository.OptionalMessageRepository;
import com.group308.socialmedia.core.model.repository.OptionalNotificationRepository;
import com.group308.socialmedia.core.model.service.OptionalNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OptionalNotificationServiceImpl implements OptionalNotificationService {


    private final OptionalNotificationRepository optionalNotificationRepository;

    @Override
    public OptionalNotification findById(Long id) {
        return optionalNotificationRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return optionalNotificationRepository.existsById(id);
    }

    @Override
    public OptionalNotification save(OptionalNotification optionalNotification) {
        return optionalNotificationRepository.save(optionalNotification);
    }

    @Override
    public void deleteById(Long id) {
        this.optionalNotificationRepository.deleteById(id);
    }


    @Override
    public List<OptionalNotification> findAllByNotificationTo(String notificationTo) {
        return optionalNotificationRepository.findAllByNotificationTo(notificationTo);
    }

    @Override
    public OptionalNotification findByNotificationToAndIsSent(String notificationTo, long isSent) {
        return optionalNotificationRepository.findByNotificationToAndIsSent(notificationTo, isSent);
    }




}
