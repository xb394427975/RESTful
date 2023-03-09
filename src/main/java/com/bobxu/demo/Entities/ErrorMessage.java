package com.bobxu.demo.Entities;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorMessage {
    private final String message;
    private final Date timestamp;

    private final HttpStatus httpStatus;


    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public ErrorMessage(String message, Date timestamp, HttpStatus httpStatus){
        this.message = message;
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
    }
}
