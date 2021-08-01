package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.exception.ResourceNotFoundException;
import com.group308.socialmedia.core.model.domain.Content;
import com.group308.socialmedia.core.model.repository.ContentRepository;
import com.group308.socialmedia.core.model.service.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;


    @Override
    public Content findById(Long id) {
        return contentRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return contentRepository.existsById(id);
    }

    @Override
    public Content save(Content content) {
        return contentRepository.save(content);
    }

    @Override
    public void deleteById(Long id) {
        this.contentRepository.deleteById(id);
    }

    @Override
    public Content findByTopic(String topic) {
        return contentRepository.findByTopic(topic).orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }

    @Override
    public Content findByGeoId(String geoId) {
        return contentRepository.findByGeoId(geoId).orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }

    @Override
    public List<Content> findAllByTopicContains(String topic) {
        return contentRepository.findAllByTopicContains(topic);
    }

    @Override
    public List<Content> findAllByGeoNameContains(String geoName) {
        return contentRepository.findAllByGeoNameContains(geoName);
    }

}
