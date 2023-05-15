package com.maksvell.httpExceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends HttpException {
    public InternalServerErrorException(String message){
        super(message);
        setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(){
        super("Internal Server Error");
        setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
