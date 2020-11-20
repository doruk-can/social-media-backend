package com.group308.socialmedia.core.model.repository;


import com.group308.socialmedia.core.model.domain.Connection;

import java.util.Optional;


public interface ConnectionRepository extends BaseRepository<Connection, Long>{

    Optional<Connection> findByFollowerIdAndFollowingId(long followerId, long followingId);


}