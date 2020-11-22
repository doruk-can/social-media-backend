package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.Subscription;

import java.util.Optional;

public interface SubscriptionRepository extends BaseRepository<Subscription, Long> {
    Optional<Subscription> findBySubscriberIdAndSubscribedContentId(long subscriberId, long subscribedContentId);

}
