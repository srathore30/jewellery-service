package com.jewellery.controller;

import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import com.jewellery.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cartRequest) {
        CartResponse response = cartService.addToCart(cartRequest.getUserId(), cartRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Page<CartResponse>> getAllCartItemByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CartResponse> response = cartService.getCartItems(userId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{cartId}/update")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long cartId,
            @RequestParam Integer quantity) {
        CartResponse response = cartService.updateCartItem(cartId, quantity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<CartResponse> removeCartItem(@PathVariable Long cartId) {
        CartResponse response = cartService.removeCartItem(cartId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
