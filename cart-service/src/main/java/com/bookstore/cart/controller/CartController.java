package com.bookstore.cart.controller;

import com.bookstore.cart.domain.Cart;
import com.bookstore.cart.domain.vo.AddItemRequest;
import com.bookstore.cart.domain.vo.CartResponse;
import com.bookstore.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCartResponseById(cartId));
    }

    @PostMapping
    public ResponseEntity<Cart> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponse> addItemToCart(@PathVariable Long cartId, @Valid @RequestBody AddItemRequest request) throws IOException {
        return ResponseEntity.ok(cartService.addItemToCart(request,cartId));
    }


}
