package com.group308.socialmedia.core.model.repository.user;


import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.repository.BaseRepository;

import java.util.Optional;

/**
 * Created by isaozturk on 5.09.2019
 */
public interface ApplicationUserRepository extends BaseRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String userName);
}
