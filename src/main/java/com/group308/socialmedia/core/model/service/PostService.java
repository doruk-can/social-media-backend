package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.dto.PostDto;
import com.group308.socialmedia.core.model.domain.Post;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostService extends BaseService<Post> {

    void update(long postId, PostDto postDto);

    List<Post> findAllByPostOwnerName(String postOwnerName);

    List<Post> findAllByPostGeoIdOrPostTopic(String geoId, String postTopic);


}
