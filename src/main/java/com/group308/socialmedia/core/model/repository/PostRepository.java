package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.Post;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends BaseRepository<Post, Long>{

    List<Post> findAllByPostOwnerName(String postOwnerName);

    List<Post> findAllByPostGeoIdOrPostTopic(String geoId, String postTopic);

}
