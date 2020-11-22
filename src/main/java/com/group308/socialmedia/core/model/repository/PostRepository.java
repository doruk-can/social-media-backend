package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.Post;

import java.util.Optional;

public interface PostRepository extends BaseRepository<Post, Long>{

    <Optional>Post findAllByPostOwnerName(String postOwnerName);

}
