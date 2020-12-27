package com.appsdev.mobileapp.ws.exceptions;

public class UserServiceException extends RuntimeException{

    private static final long serialVersionUID = 762979753664005861L;

    public UserServiceException(String message) {
        super(message);
    }
}
