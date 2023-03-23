package com.bookstore.cart.controller.exception;

import com.bookstore.cart.service.exception.CartNotExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { CartNotExistException.class })
    protected ResponseEntity<Object> handleResourceNotFound(CartNotExistException ex, WebRequest request) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDetail errorDetails = new ErrorDetail(LocalDateTime.now(), status.value(), CartNotExistException.class.getSimpleName(),ex.getMessage(), path);
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), status);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        String bodyOfResponse = "An error occurred while processing your request";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
