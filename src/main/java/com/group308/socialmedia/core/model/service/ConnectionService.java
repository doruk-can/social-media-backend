package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.Connection;

import java.util.List;

public interface ConnectionService extends BaseService<Connection>{

    Connection findByFollowerIdAndFollowingId(long followerId, long followingId);

    List<Connection> findAllByFollowerId(long followerId);

    List<Connection> findAllByFollowingId(long followingId);

}
