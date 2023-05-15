package com.maksvell.exceptionHandlers;

import com.maksvell.dto.ExceptionMessageDto;
import com.maksvell.httpExceptions.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionMessageDto> catchHttpException(HttpException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionMessageDto(e.getStatusCode().value(), e.getMessage()), e.getStatusCode());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageDto> catchValidationException(MethodArgumentNotValidException e) {
        String errorMsg = "validation is failed!";
        if (e.getErrorCount() > 0) {
            List<String> errorDetails = new ArrayList<>();
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                errorDetails.add(error.getDefaultMessage());
            }

            if (errorDetails.size() > 0) errorMsg = errorDetails.get(0);
        }
        log.error(errorMsg);
        return new ResponseEntity<>(new ExceptionMessageDto(e.getStatusCode().value(), errorMsg), e.getStatusCode());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageDto> catchIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionMessageDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity <Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionMessageDto(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
