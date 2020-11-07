package com.group308.socialmedia.core.dto.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * Created by isaozturk on 1.08.2020
 */
@AllArgsConstructor
public enum Status implements Serializable {

    SUCCESS(0),
    RESOURCE_EXIST(1),
    RESOURCE_NOT_FOUND(10),
    NOT_DEFINED_PARAMETER(11),
    METHOD_ARGUMENT_NOT_VALID(20),
    BIND_EXCEPTION(30),
    TYPE_MISMATCH(40),
    MISSING_REQUEST_PART(50),
    MISSING_REQUEST_PARAMETER(60),
    METHOD_ARGUMENT_TYPE_MISMATCH(70),
    CONSTRAINT_VIOLATION(80),
    NO_HANDLER_FOUND(90),
    HTTP_MESSAGE_NOT_READABLE(100),
    MISSING_PATH_VARIABLE(110),
    SYSTEM_ERROR(1000);

    private final int value;

    @JsonValue
    public int getValue() {

        return this.value;
    }
}