package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.dto.PostDto;
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

    public void update(long postId, PostDto postDto) {

        Post post = findById(postId);
        post.setPostGeoId(postDto.getPostGeoId());
        post.setPostGeoName(postDto.getPostGeoName());
        post.setPostImage(postDto.getPostImage());
        post.setPostOwnerName(postDto.getPostOwnerName());
        post.setPostText(postDto.getPostText());
        post.setPostTopic(postDto.getPostTopic());
        post.setPostVideoURL(postDto.getPostVideoURL());

        save(post);
    }

    @Override
    public List<Post> findAllByPostOwnerName(String postOwnerName) {
        return postRepository.findAllByPostOwnerName(postOwnerName);
    }

    @Override
    public List<Post> findAllByPostGeoIdOrPostTopic(String geoId, String postTopic) {
        return postRepository.findAllByPostGeoIdOrPostTopic(geoId, postTopic);
    }


}