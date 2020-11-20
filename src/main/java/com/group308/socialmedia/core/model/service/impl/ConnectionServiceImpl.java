package com.group308.socialmedia.core.model.service.impl;

import com.group308.socialmedia.core.exception.ResourceNotFoundException;
import com.group308.socialmedia.core.model.domain.Connection;
import com.group308.socialmedia.core.model.repository.ConnectionRepository;
import com.group308.socialmedia.core.model.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Override
    public Connection findById(Long id) {
        return connectionRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return connectionRepository.existsById(id);
    }

    @Override
    public Connection save(Connection connection) {
        return connectionRepository.save(connection);
    }

    @Override
    public void deleteById(Long id) {
        this.connectionRepository.deleteById(id);
    }

    @Override
    public Connection findByFollowerIdAndFollowingId(long followerId, long followingId){
        return connectionRepository.findByFollowerIdAndFollowingId(followerId, followingId).orElseThrow(()->new ResourceNotFoundException("NotFound.general.message"));
    }
}
