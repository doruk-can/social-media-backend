package com.group308.socialmedia.core.model.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by isaozturk on 10.09.2019
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    T save(T entity);

    void deleteById(ID id);


}

