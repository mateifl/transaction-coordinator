package ro.zizicu.transaction.coordinator.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import ro.zizicu.transaction.coordinator.exceptions.DistributedTransactionNotFound;
import ro.zizicu.transaction.coordinator.exceptions.RestError;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DistributedTransactionNotFound.class, DataAccessException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest webRequest) {
    	log.error(exception.getMessage());
        return handleExceptionInternal(exception,
                new RestError(exception.getMessage(), 1),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                webRequest);
    }
}
