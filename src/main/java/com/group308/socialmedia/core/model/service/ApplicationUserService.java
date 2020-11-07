package com.group308.socialmedia.core.model.service;


import com.group308.socialmedia.core.model.domain.user.ApplicationUser;

/**
 * Created by isaozturk on 5.09.2019
 */
public interface ApplicationUserService extends BaseService<ApplicationUser> {

    ApplicationUser findByUsername(String userName);
}
