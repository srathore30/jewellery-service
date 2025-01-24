package com.jewellery.controller;

import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import com.jewellery.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartResponse> addToCart(@PathVariable Long userId, @RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(cartService.addToCart(userId, cartRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartResponse>> getCartItems(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PutMapping("/{cartId}/update")
    public ResponseEntity<CartResponse> updateCartItem(@PathVariable Long cartId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(cartId, quantity));
    }

    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartId) {
        cartService.removeCartItem(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
