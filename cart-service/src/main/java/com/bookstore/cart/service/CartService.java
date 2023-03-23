package com.bookstore.cart.service;

import com.bookstore.cart.domain.Cart;
import com.bookstore.cart.domain.vo.AddItemRequest;
import com.bookstore.cart.domain.vo.CartResponse;

import java.io.IOException;

public interface CartService {

    Cart createCart();
    CartResponse getCartResponseById(Long cartId);
    CartResponse addItemToCart(AddItemRequest request, Long cartId) throws IOException;
    Cart removeItemToCart();
    boolean deleteCart();
}
