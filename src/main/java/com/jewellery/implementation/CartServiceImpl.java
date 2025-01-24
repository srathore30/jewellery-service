package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import com.jewellery.entities.CartEntity;
import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.UserEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.repositories.CartRepo;
import com.jewellery.repositories.ProductRepo;
import com.jewellery.repositories.UserRepo;
import com.jewellery.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    @Override
    public CartResponse addToCart(Long userId, CartRequest cartRequest) {
        CartEntity cart = mapToEntity(userId, cartRequest);
        Optional<CartEntity> existingCartOpt = cartRepo.findByUserEntityIdAndProductEntityId(userId, cartRequest.getProductId());
        if (existingCartOpt.isPresent()) {
            CartEntity existingCart = existingCartOpt.get();
            existingCart.setQuantity(existingCart.getQuantity() + cartRequest.getQuantity());
            return mapToResponse(cartRepo.save(existingCart));
        }
        return mapToResponse(cartRepo.save(cart));
    }


    @Override
    public Page<CartResponse> getCartItems(Long userId, int page, int size) {
        Page<CartEntity> cartPage = cartRepo.findByUserEntityId(userId, PageRequest.of(page, size));
        return cartPage.map(this::mapToResponse);
    }

    @Override
    public CartResponse updateCartItem(Long cartId, Integer quantity) {
        CartEntity cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorMessage())
                );

        cart.setQuantity(quantity);
        return mapToResponse(cartRepo.save(cart));
    }

    @Override
    public CartResponse removeCartItem(Long cartId) {
        CartEntity cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorMessage())
                );
        cartRepo.delete(cart);
        return mapToResponse(cart);
    }


    private CartEntity mapToEntity(Long userId, CartRequest cartRequest) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.USER_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.USER_NOT_FOUND.getErrorMessage())
                );

        ProductEntity product = productRepo.findById(cartRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage())
                );

        CartEntity cart = new CartEntity();
        cart.setUserEntity(user);
        cart.setProductEntity(product);
        cart.setQuantity(cartRequest.getQuantity());
        return cart;
    }

    private CartResponse mapToResponse(CartEntity cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setProductName(cart.getProductEntity().getTitle());
        response.setProductPrice(cart.getProductEntity().getSellingPrice());
        response.setQuantity(cart.getQuantity());
        response.setTotalPrice(cart.getQuantity() * cart.getProductEntity().getSellingPrice());
        return response;
    }
}
