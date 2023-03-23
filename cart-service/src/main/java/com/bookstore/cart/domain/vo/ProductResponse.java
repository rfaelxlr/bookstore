package com.bookstore.cart.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {
    private String code;
    private String name;
    private String description;
    private Double salesPrice;
}
