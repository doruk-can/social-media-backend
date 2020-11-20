package com.group308.socialmedia.core.model.service;


import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.domain.user.ApplicationUserRoleRelation;


public interface ApplicationUserService extends BaseService<ApplicationUser> {

    <Optional>ApplicationUser findByUsername(String userName);

    <Optional>ApplicationUser findByEmail(String email);

    <Optional>ApplicationUser findByEmailAndUsername(String email, String userName);

}
