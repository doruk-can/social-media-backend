package com.group308.socialmedia.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by isaozturk on 22.12.2018
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends DefaultException {

    /**
     * You can use the message {@code DefaultException.ResourceNotFoundException} it's value is
     * {@code DefaultException.ResourceNotFoundException = Resource not found! {0} {1}} where
     * {@code {0}} is resource name and {@code {1}} is resource identifier.
     *
     * @param errorCode
     * @param messageArguments
     */
    public ResourceNotFoundException(String errorCode, Object... messageArguments) {
        super(errorCode, messageArguments);
    }
}