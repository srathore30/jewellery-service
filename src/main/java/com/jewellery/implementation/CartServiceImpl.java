package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.constant.InventoryStatus;
import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import com.jewellery.entities.CartEntity;
import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.UserEntity;
import com.jewellery.repositories.CartRepo;
import com.jewellery.repositories.ProductRepo;
import com.jewellery.repositories.UserRepo;
import com.jewellery.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Override
    public CartResponse addToCart(Long userId, CartRequest cartRequest) {

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException(ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));

        ProductEntity product = productRepo.findById(cartRequest.getProductId())
                .orElseThrow(() -> new RuntimeException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));

        InventoryStatus inventoryStatus = productRepo.getInventoryStatusById(cartRequest.getProductId());
        if (inventoryStatus == InventoryStatus.OUT_OF_STOCK) {
            throw new RuntimeException(ApiErrorCodes.PRODUCT_OUT_OF_STOCK.getErrorMessage());
        }

        CartEntity existingCart = cartRepo.findByUserEntityIdAndProductEntityId(userId, cartRequest.getProductId())
                .orElse(null);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + cartRequest.getQuantity());
            return mapToResponse(cartRepo.save(existingCart));
        }

        CartEntity cart = new CartEntity();
        cart.setUserEntity(user);
        cart.setProductEntity(product);
        cart.setQuantity(cartRequest.getQuantity());
        return mapToResponse(cartRepo.save(cart));
    }

    @Override
    public List<CartResponse> getCartItems(Long userId) {
        return cartRepo.findAll().stream()
                .filter(cart -> cart.getUserEntity().getId().equals(userId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse updateCartItem(Long cartId, Integer quantity) {
        CartEntity cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException(ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorMessage()));

        cart.setQuantity(quantity);
        return mapToResponse(cartRepo.save(cart));
    }

    @Override
    public void removeCartItem(Long cartId) {
        if (!cartRepo.existsById(cartId)) {
            throw new RuntimeException(ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorMessage());
        }
        cartRepo.deleteById(cartId);
    }

    @Override
    public void clearCart(Long userId) {
        List<CartEntity> userCart = cartRepo.findAll().stream()
                .filter(cart -> cart.getUserEntity().getId().equals(userId))
                .collect(Collectors.toList());

        if (userCart.isEmpty()) {
            throw new RuntimeException(ApiErrorCodes.CART_EMPTY.getErrorMessage());
        }

        cartRepo.deleteAll(userCart);
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
