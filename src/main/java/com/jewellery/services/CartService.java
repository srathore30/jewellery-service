package com.jewellery.services;


import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    CartResponse addToCart(Long userId, CartRequest cartRequest);
    Page<CartResponse> getCartItems(Long userId, int page, int size);
    CartResponse updateCartItem(Long cartId, Integer quantity);
    CartResponse removeCartItem(Long cartId);
}
