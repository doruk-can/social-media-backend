package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.Connection;

public interface ConnectionService extends BaseService<Connection>{

    Connection findByFollowerIdAndFollowingId(long followerId, long followingId);
}
