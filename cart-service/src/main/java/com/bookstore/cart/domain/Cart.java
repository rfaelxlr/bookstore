package com.bookstore.cart.domain;

import com.bookstore.cart.domain.vo.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private UUID externalId;

    private boolean active;

    @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL)
    private Set<CartItem> items;


    public static Cart createCart() {
        return Cart.builder().active(true)
                .externalId(UUID.randomUUID()).build();
    }

    public Set<CartItem> addItem(Integer quantity, ProductResponse product) {
        CartItem cartItem = new CartItem(product, quantity, this);

        Set<CartItem> itemsSet = new HashSet<>();
        itemsSet.add(cartItem);

        if (CollectionUtils.isEmpty(this.getItems())) {
            this.setItems(itemsSet);
            return Collections.EMPTY_SET;
        }
        Set<CartItem> itemsToRemove = new HashSet<>();
        if (this.isHasItem(product.getCode())) {
            itemsToRemove.addAll(this.getItems().stream()
                    .filter(item -> item.getCode().equals(product.getCode())).collect(Collectors.toSet()));
            this.getItems().removeIf(item -> item.getCode().equals(product.getCode()));
        }
        this.getItems().addAll(itemsSet);
        return itemsToRemove;
    }

    private boolean isHasItem(String productCode) {
        return !CollectionUtils.isEmpty(this.getItems().stream()
                .filter(item -> item.getCode().equals(productCode)).collect(Collectors.toSet()));
    }

    public boolean isHasItem(Integer quantity, String productCode) {
        if (CollectionUtils.isEmpty(this.getItems())) {
            return false;
        }
        return !CollectionUtils.isEmpty(this.getItems().stream()
                .filter(item -> item.getQuantity().equals(quantity) && item.getCode().equals(productCode)).collect(Collectors.toSet()));
    }
}
