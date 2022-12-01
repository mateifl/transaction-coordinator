package ro.zizicu.transaction.coordinator.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.zizicu.transaction.coordinator.exceptions.DistributedTransactionNotFound;
import ro.zizicu.transaction.coordinator.exceptions.RestError;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { DistributedTransactionNotFound.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest webRequest) {
        return handleExceptionInternal(exception,
                new RestError("Distributed transaction not found", 1),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                webRequest);
    }
}
