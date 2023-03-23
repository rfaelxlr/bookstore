package com.bookstore.cart.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ErrorDetail {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
