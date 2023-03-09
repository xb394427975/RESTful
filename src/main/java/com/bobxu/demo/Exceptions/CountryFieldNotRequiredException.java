package com.bobxu.demo.Exceptions;

public class CountryFieldNotRequiredException extends RuntimeException{
    public CountryFieldNotRequiredException(String message){
        super(message);
    }

}
