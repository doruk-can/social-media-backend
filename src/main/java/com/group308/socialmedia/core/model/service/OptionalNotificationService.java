package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.OptionalMessage;
import com.group308.socialmedia.core.model.domain.OptionalNotification;

import java.util.List;

public interface OptionalNotificationService extends BaseService<OptionalNotification> {

    List<OptionalNotification> findAllByNotificationTo(String notificationTo);

    OptionalNotification findByNotificationToAndIsSent(String notificationTo, long isSent);


}
