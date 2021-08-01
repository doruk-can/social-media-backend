package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.model.domain.OptionalMessage;
import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.domain.PostInteraction;
import com.group308.socialmedia.core.model.repository.OptionalMessageRepository;
import com.group308.socialmedia.core.model.service.OptionalMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OptionalMessageServiceImpl implements OptionalMessageService {

    private final OptionalMessageRepository optionalMessageRepository;

    @Override
    public OptionalMessage findById(Long id) {
        return optionalMessageRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return optionalMessageRepository.existsById(id);
    }

    @Override
    public OptionalMessage save(OptionalMessage optionalMessage) {
        return optionalMessageRepository.save(optionalMessage);
    }

    @Override
    public void deleteById(Long id) {
        this.optionalMessageRepository.deleteById(id);
    }


    @Override
    public List<OptionalMessage> findAllByMessageFromAndMessageTo(String messageFrom, String messageTo) {
        return optionalMessageRepository.findAllByMessageFromAndMessageTo(messageFrom, messageTo);
    }



}
