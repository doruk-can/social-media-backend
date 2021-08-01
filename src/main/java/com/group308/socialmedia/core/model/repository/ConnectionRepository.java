package com.group308.socialmedia.core.model.repository;


import com.group308.socialmedia.core.model.domain.Connection;

import java.util.List;
import java.util.Optional;


public interface ConnectionRepository extends BaseRepository<Connection, Long>{

    Optional<Connection> findByFollowerIdAndFollowingId(long followerId, long followingId);

    List<Connection> findAllByFollowerId(long followerId);

    List<Connection> findAllByFollowingId(long followingId);



}