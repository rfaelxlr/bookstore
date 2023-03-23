package com.bookstore.cart.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductNotExistException extends RuntimeException{
    private String message;
}
