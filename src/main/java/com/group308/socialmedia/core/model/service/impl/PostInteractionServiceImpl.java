package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.exception.ResourceNotFoundException;
import com.group308.socialmedia.core.model.domain.Connection;
import com.group308.socialmedia.core.model.domain.PostInteraction;
import com.group308.socialmedia.core.model.repository.PostInteractionRepository;
import com.group308.socialmedia.core.model.service.PostInteractionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostInteractionServiceImpl implements PostInteractionService {

    private final PostInteractionRepository postInteractionRepository;

    @Override
    public PostInteraction findById(Long id) {
        return postInteractionRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return postInteractionRepository.existsById(id);
    }

    @Override
    public PostInteraction save(PostInteraction postInteraction) {
        return postInteractionRepository.save(postInteraction);
    }

    @Override
    public void deleteById(Long id) {
        this.postInteractionRepository.deleteById(id);
    }

    @Override
    public PostInteraction findByPostIdAndCommentatorIdAndInteractionType(long postId, long commentatorId, String interactionType){
        return postInteractionRepository.findByPostIdAndCommentatorIdAndInteractionType(postId, commentatorId, interactionType).orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }


}
