package com.maksvell.httpExceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class HttpException extends RuntimeException {
    protected HttpStatus statusCode;

    public HttpException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public HttpException(String message){
        super(message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
