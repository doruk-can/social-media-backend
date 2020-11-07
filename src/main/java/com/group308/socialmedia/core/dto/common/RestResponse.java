package com.group308.socialmedia.core.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by isaozturk on 1.08.2020
 */
@Data
@NoArgsConstructor
public class RestResponse<T> implements Serializable {

    private static final RestResponse<Void> EMPTY_RESPONSE = new RestResponse<>(null, null, null,null);

    private T data;

    private Status status;

    private String message;

    private List<ValidationMessage> validations;

    private RestResponse(T t, Status status, String message, List<ValidationMessage> validations){
        this.data = t;
        this.status = status;
        this.message = message;
        this.validations = validations;
    }

    public static <T> RestResponse<T> of(T t, Status status, String message){
        return new RestResponse<>(t, status, message, null);
    }

    public static <T> RestResponse<T> of(T t, Status status, String message, List<ValidationMessage> validations){
        return new RestResponse<>(t, status, message, validations);
    }

    public static RestResponse<Void> empty() {
        return EMPTY_RESPONSE;
    }

}
