package com.bookstore.cart.service.impl;

import com.bookstore.cart.domain.Cart;
import com.bookstore.cart.domain.CartItem;
import com.bookstore.cart.domain.vo.AddItemRequest;
import com.bookstore.cart.domain.vo.CartResponse;
import com.bookstore.cart.domain.vo.ProductResponse;
import com.bookstore.cart.feign.catalog.CatalogFeignClient;
import com.bookstore.cart.repository.CartItemRepository;
import com.bookstore.cart.repository.CartRepository;
import com.bookstore.cart.service.CartService;
import com.bookstore.cart.service.exception.CartNotExistException;
import com.bookstore.cart.service.exception.ProductNotExistException;
import feign.Response;
import feign.jackson.JacksonDecoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Set;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CatalogFeignClient catalogClient;

    private final CartItemRepository cartItemRepository;

    @Override
    public Cart createCart() {
        Cart cart = Cart.createCart();
        return cartRepository.save(cart);
    }

    @Override
    public CartResponse getCartResponseById(Long cartId) {
        return cartRepository.findById(cartId)
                .map(CartResponse::new)
                .orElseThrow(() -> new CartNotExistException(String.format("Cart for id %d not exist!", cartId)));
    }

    private Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotExistException(String.format("Cart for id %d not exist!", cartId)));
    }

    @Override
    public CartResponse addItemToCart(AddItemRequest request, Long cartId) throws IOException {
        Cart cart = getCartById(cartId);
        if(cart.isHasItem(request.getQuantity(), request.getProductCode())){
            return new CartResponse(cart);
        }

        Response response = catalogClient.getProduct(request.getProductCode());

        if (response.status() == 200) {
            ProductResponse product = (ProductResponse) new JacksonDecoder()
                    .decode(response, ProductResponse.class);
            Set<CartItem> itemsToRemove = cart.addItem(request.getQuantity(), product);
            if(!CollectionUtils.isEmpty(itemsToRemove)){
                cartItemRepository.deleteAll(itemsToRemove);
            }
            cartRepository.save(cart);
            return new CartResponse(cart);
        }

        throw new ProductNotExistException(String.format("Cart for id %s not exist!", request.getProductCode()));
    }

    @Override
    public Cart removeItemToCart() {
        return null;
    }

    @Override
    public boolean deleteCart() {
        return false;
    }


}
