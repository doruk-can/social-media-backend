package com.group308.socialmedia.core.model.service.impl;


import com.group308.socialmedia.core.exception.ResourceNotFoundException;
import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.domain.Subscription;
import com.group308.socialmedia.core.model.repository.SubscriptionRepository;
import com.group308.socialmedia.core.model.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return subscriptionRepository.existsById(id);
    }

    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteById(Long id) {
        this.subscriptionRepository.deleteById(id);
    }

    @Override
    public Subscription findBySubscriberIdAndSubscribedContentId(long subscriberId, long subscribedContentId) {
        return subscriptionRepository.findBySubscriberIdAndSubscribedContentId(subscriberId,subscribedContentId).
                orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }

}
