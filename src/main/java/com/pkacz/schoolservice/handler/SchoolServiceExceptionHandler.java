package com.pkacz.schoolservice.handler;

import com.pkacz.schoolservice.model.dto.ErrorMessageDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SchoolServiceExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto illegalArgumentExceptionErrorResponse(IllegalArgumentException illegalArgumentException) {
        return new ErrorMessageDto(illegalArgumentException.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto argumentNotValidErrorResponse(ConstraintViolationException exception) {
        return new ErrorMessageDto(exception.getMessage());
    }

}
