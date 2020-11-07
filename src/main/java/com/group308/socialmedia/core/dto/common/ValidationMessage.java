package com.group308.socialmedia.core.dto.common;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by isaozturk on 1.08.2020
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ValidationMessage implements Serializable {

    private String field;

    private String code;

    private String message;

    private Severity severity;

    public ValidationMessage(String field, String code, String message, Severity severity) {
        this.code = code;
        this.message = message;
        this.severity = severity;
        this.field =field;
    }
    public ValidationMessage(String code, String message, Severity severity) {
        this.code = code;
        this.message = message;
        this.severity = severity;
    }
}
