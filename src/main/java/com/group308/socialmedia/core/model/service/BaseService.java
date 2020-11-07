package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.AbstractEntity;

/**
 * Created by isaozturk on 18.09.2019
 */
public interface BaseService<T extends AbstractEntity> {

    T findById(Long id);

    boolean existsById(Long id);

    T save(T t);

    void deleteById(Long id);
}
