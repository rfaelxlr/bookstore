package com.bookstore.cart.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemResponse {
    private String productCode;
    private String name;
    private String description;
    private Double price;

    private Integer quantity;
}
