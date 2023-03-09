package com.bobxu.demo.Exceptions;

public class CountryNotFoundException extends RuntimeException{
    public CountryNotFoundException(String message){
        super(message);
    }

}
