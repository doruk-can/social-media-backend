package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.Content;

import java.util.List;

public interface ContentService extends BaseService<Content> {
    Content findByTopic(String topic);
    Content findByGeoId(String geoId);
    List<Content> findAllByTopicContains(String topic);
    List<Content> findAllByGeoNameContains(String geoName);
}
