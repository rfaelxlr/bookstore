package com.bookstore.cart.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AddItemRequest {

    @NotBlank(message = "The productCode must be filled!")
    @Size(max = 10, message = "The productCode must have maximum 10 digits!")
    private String productCode;

    @NotNull(message = "The quantity must be filled!")
    private Integer quantity;
}
