package com.group308.socialmedia.core.model.repository.user;


import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserRepository extends BaseRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String userName);

    Optional<ApplicationUser> findByEmail(String email);

    Optional<ApplicationUser> findByEmailAndUsername(String email, String userName);

    List<ApplicationUser> findAllByUsernameContains(String keyword);

}
