package com.bookstore.cart.domain.vo;

import com.bookstore.cart.domain.Cart;
import com.bookstore.cart.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartResponse {

    private Long id;

    private UUID externalId;

    private boolean active;

    private BigDecimal totalPrice;

    private Set<CartItemResponse> items;

    public CartResponse(Cart cart) {
        this.setId(cart.getId());
        this.setExternalId(cart.getExternalId());
        this.setActive(cart.isActive());
        if (CollectionUtils.isEmpty(cart.getItems()))
            this.setItems(Collections.EMPTY_SET);

        this.setItems(cart.getItems().stream().map(CartItem::toCartItemResponse).collect(Collectors.toSet()));
        this.setTotalPrice(BigDecimal.valueOf(cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum()));
    }
}
