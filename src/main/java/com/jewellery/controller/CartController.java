package com.jewellery.controller;

import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<CartResponse> createCartItem(@RequestBody CartRequest cartRequest) {
        CartResponse response = cartService.addToCart(cartRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartItemById(@PathVariable Long cartId) {
        CartResponse response = cartService.getCartItemById(cartId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long cartId, @RequestParam Integer quantity) {
        CartResponse response = cartService.updateCartItem(cartId, quantity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllByUserId")
    public ResponseEntity<PaginatedResp<CartResponse>> getCartByUserId(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        PaginatedResp<CartResponse> response = cartService.getAllCartItemsByUserId(userId, page, size, sortBy, sortDirection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItemById(@PathVariable Long cartId) {
        cartService.removeCartItem(cartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
