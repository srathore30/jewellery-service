package com.jewellery.services;


import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;

import java.util.List;

public interface CartService {

    CartResponse addToCart(Long userId, CartRequest cartRequest);
    List<CartResponse> getCartItems(Long userId);
    CartResponse updateCartItem(Long cartId, Integer quantity);
    void removeCartItem(Long cartId);
    void clearCart(Long userId);
}
