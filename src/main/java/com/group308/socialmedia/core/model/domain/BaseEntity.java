package com.group308.socialmedia.core.model.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by isaozturk on 9.12.2019
 */
public interface BaseEntity extends Serializable {

    Long getId();

    String getCreatedUser();

    String getUpdatedUser();

    Date getCreatedDate();

    Date getUpdatedDate();
}
