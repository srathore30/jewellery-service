package com.jewellery.services;


import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import com.jewellery.dto.res.util.PaginatedResp;


public interface CartService {
    CartResponse addToCart(CartRequest cartRequest);
    CartResponse getCartItemById(Long cartId);
    PaginatedResp<CartResponse> getAllCartItemsByUserId(Long userId, int page, int size, String sortBy, String sortDirection);
    CartResponse updateCartItem(Long cartId, Integer quantity);
    CartResponse removeCartItem(Long cartId);
}
