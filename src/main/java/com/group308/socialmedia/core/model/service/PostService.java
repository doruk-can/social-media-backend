package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.dto.PostDto;
import com.group308.socialmedia.core.model.domain.Post;

public interface PostService extends BaseService<Post> {

    void update(long postId, PostDto postDto);

}
