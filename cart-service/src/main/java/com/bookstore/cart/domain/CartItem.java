package com.bookstore.cart.domain;

import com.bookstore.cart.domain.vo.CartItemResponse;
import com.bookstore.cart.domain.vo.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    private String code;
    private String name;
    private String description;
    private Double price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cartId;

    public CartItem(ProductResponse product, Integer quantity, Cart cart) {
        this.code = product.getCode();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getSalesPrice();
        this.quantity = quantity;
        this.cartId = cart;
    }

    public CartItemResponse toCartItemResponse() {
        return new CartItemResponse(this.getCode(), this.getName(), this.getDescription(), this.getPrice(), this.getQuantity());
    }
}
