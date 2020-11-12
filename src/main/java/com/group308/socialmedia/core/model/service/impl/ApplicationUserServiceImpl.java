package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.exception.ResourceNotFoundException;
import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.repository.user.ApplicationUserRepository;
import com.group308.socialmedia.core.model.service.ApplicationUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by isaozturk on 5.09.2019
 */
@Service
@AllArgsConstructor
class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public ApplicationUser findByUsername(String userName) {
        return applicationUserRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }

    @Override
    public ApplicationUser findByEmail(String email) {
        return applicationUserRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }

    @Override
    public ApplicationUser findByEmailAndUsername(String email, String userName) {
        return applicationUserRepository.findByEmailAndUsername(email, userName).orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }

    @Override
    public ApplicationUser findById(Long id) {
        return applicationUserRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }

    @Override
    public boolean existsById(Long id) {
        return applicationUserRepository.existsById(id);
    }

    @Override
    public ApplicationUser save(ApplicationUser entity) {
        return applicationUserRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        applicationUserRepository.deleteById(id);
    }

}
