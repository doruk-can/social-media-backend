package com.group308.socialmedia.core.model.repository.user;


import com.group308.socialmedia.core.model.domain.user.ApplicationUserRoleRelation;
import com.group308.socialmedia.core.model.repository.BaseRepository;

import java.util.List;

/**
 * Created by isaozturk on 5.09.2019
 */
public interface ApplicationUserRoleRelationRepository extends BaseRepository<ApplicationUserRoleRelation, Long> {

    List<ApplicationUserRoleRelation> findByUserId(Long userId);
}
