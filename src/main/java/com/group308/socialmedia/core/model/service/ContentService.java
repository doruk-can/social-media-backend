package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.Content;

public interface ContentService extends BaseService<Content> {
    Content findByTopic(String topic);
    Content findByGeoId(String geoId);
}
