package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.Content;

import java.util.Optional;

public interface ContentRepository extends BaseRepository<Content, Long> {
    Optional<Content> findByTopic(String topic);
    Optional<Content> findByGeoId(long geoId);
}
