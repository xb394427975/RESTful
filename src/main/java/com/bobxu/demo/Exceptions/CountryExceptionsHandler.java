package com.bobxu.demo.Exceptions;

import com.bobxu.demo.Entities.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CountryExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value ={CountryNotFoundException.class})
    public ResponseEntity<Object> handleCountryNotFoundException(CountryNotFoundException ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),new Date(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value ={CountryFieldNotRequiredException.class})
    public ResponseEntity<Object> handleCountryFieldNotRequiredException(CountryFieldNotRequiredException ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),new Date(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }
}
