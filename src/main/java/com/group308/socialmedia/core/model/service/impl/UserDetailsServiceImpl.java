package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.model.domain.user.ApplicationRole;
import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.repository.user.ApplicationRoleRepository;
import com.group308.socialmedia.core.model.repository.user.ApplicationUserRepository;
import com.group308.socialmedia.core.model.repository.user.ApplicationUserRoleRelationRepository;
import com.group308.socialmedia.core.security.JwtUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaozturk on 1.08.2020
 */
@Service
@AllArgsConstructor
@Qualifier("UserDetailsServiceImpl")
class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    private final ApplicationUserRoleRelationRepository userRoleRepository;

    private final ApplicationRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final ApplicationUser appUser = applicationUserRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(String.format("The username %s doesn't exist", username)));


        List<GrantedAuthority> authorities = new ArrayList<>();

        userRoleRepository.findByUserId(appUser.getId()).forEach(r -> {

            ApplicationRole role = roleRepository.findById(r.getRoleId())
                    .orElseThrow(()->new UsernameNotFoundException(String.format("The role %s doesn't exist", username)));

            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        });

        return new JwtUser(appUser.getId(),
                appUser.getUsername(),
                appUser.getPassword(),
                authorities,
                appUser.getLastPasswordResetDate());
    }

}
