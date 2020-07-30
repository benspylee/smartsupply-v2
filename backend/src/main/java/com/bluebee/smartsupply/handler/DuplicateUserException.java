package com.bluebee.smartsupply.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String s) {
        super(s);
    }
}
