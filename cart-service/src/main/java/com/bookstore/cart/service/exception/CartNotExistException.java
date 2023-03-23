package com.bookstore.cart.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartNotExistException extends RuntimeException{
    private String message;
}
