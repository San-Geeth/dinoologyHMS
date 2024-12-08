package com.dinoology.hms.common_utility.response;

import com.dinoology.hms.common_utility.constants.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 7:33 PM
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "message", "data"})
public class ResponseWrapper<T> implements Serializable {

    private String message;
    private String timestamp = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime().toString();
    private T data;

    public ResponseWrapper() {
    }

    public ResponseWrapper<T> responseOk(T data) {
        message = ApplicationConstants.RESPONSE_OK;
        this.data = data;
        return this;
    }

    public ResponseWrapper<T> responseOk(String msg, T data) {
        message = msg;
        this.data = data;
        return this;
    }

    public ResponseWrapper<T> responseFail(T data) {
        message = (String) data;
        this.data = data;
        return this;
    }

    public ResponseWrapper(String message, T data) {
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{"
                + "message='" + message + '\''
                + ", data=" + data
                + '}';
    }
}
