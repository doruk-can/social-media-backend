package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.OptionalMessage;
import com.group308.socialmedia.core.model.domain.OptionalNotification;

import java.util.List;

public interface OptionalNotificationRepository extends BaseRepository<OptionalNotification, Long>{

    List<OptionalNotification> findAllByNotificationTo(String notificationTo);

    OptionalNotification findByNotificationToAndIsSent(String notificationTo, long isSent);

}
