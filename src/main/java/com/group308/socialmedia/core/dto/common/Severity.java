package com.group308.socialmedia.core.dto.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * Created by isaozturk on 1.08.2020
 */
@AllArgsConstructor
public enum Severity implements Serializable {

    INFO("info"), WARNING("warning"), ERROR("error"), SUCCESS("success"), REGULAR("regular");

    private final String value;

    @JsonValue
    public String getValue() {

        return this.value;
    }
}
