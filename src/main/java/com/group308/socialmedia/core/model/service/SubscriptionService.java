package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.Subscription;

import java.util.List;

public interface SubscriptionService extends BaseService<Subscription> {
    Subscription findBySubscriberIdAndSubscribedContentId(long subscriberId, long subscribedContentId);

    List<Subscription> findAllBySubscriberId(long subscriberId);
}
