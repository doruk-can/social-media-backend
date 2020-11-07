package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.repository.PostRepository;
import com.group308.socialmedia.core.model.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepository.deleteById(id);
    }

}