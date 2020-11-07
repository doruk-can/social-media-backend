package com.group308.socialmedia.core.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by isaozturk on 1.08.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SuperDto implements Serializable {

    private Integer id;
}
