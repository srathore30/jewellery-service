package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.dto.req.cart.CartRequest;
import com.jewellery.dto.res.cart.CartResponse;
import com.jewellery.dto.res.product.ProductRes;
import com.jewellery.dto.res.specification.SpecificationRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.CartEntity;
import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.SpecificationEntity;
import com.jewellery.entities.UserEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.exception.ValidationException;
import com.jewellery.repositories.CartRepo;
import com.jewellery.repositories.ProductRepo;
import com.jewellery.repositories.UserRepo;
import com.jewellery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jewellery.implementation.ProductServiceImpl.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Override
    public CartResponse addToCart(CartRequest cartRequest) {
        Long userId = cartRequest.getUserId();
        boolean exists = cartRepo.existsByUserEntityIdAndProductEntityId(userId, cartRequest.getProductId());
        if (exists) {
            throw new ValidationException(
                    ApiErrorCodes.PRODUCT_ALREADY_EXIST.getErrorCode(),
                    ApiErrorCodes.PRODUCT_ALREADY_EXIST.getErrorMessage()
            );
        }
        CartEntity cartEntity = mapToEntity(userId, cartRequest);
        CartEntity savedCart = cartRepo.save(cartEntity);
        return mapToResponse(savedCart);
    }
    @Override
    public CartResponse getCartItemById(Long cartId) {
        CartEntity cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorMessage()
                ));
        return mapToResponse(cart);
    }

    @Override
    public PaginatedResp<CartResponse> getAllCartItemsByUserId(Long userId, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CartEntity> cartPage = cartRepo.findByUserEntityId(userId, pageable);

        List<CartResponse> responseList = cartPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                cartPage.getTotalElements(),
                cartPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public CartResponse updateCartItem(Long cartId, Integer quantity) {
        CartEntity cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorMessage()
                ));

        cart.setQuantity(quantity);
        return mapToResponse(cartRepo.save(cart));
    }

    @Override
    public CartResponse removeCartItem(Long cartId) {
        CartEntity cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.CART_ITEM_NOT_FOUND.getErrorMessage()
                ));

        cartRepo.delete(cart);
        return mapToResponse(cart);
    }

    private CartEntity mapToEntity(Long userId, CartRequest cartRequest) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.USER_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()
                ));

        ProductEntity product = productRepo.findById(cartRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()
                ));

        CartEntity cart = new CartEntity();
        cart.setUserEntity(user);
        cart.setProductEntity(product);
        cart.setQuantity(cartRequest.getQuantity());
        return cart;
    }

    private CartResponse mapToResponse(CartEntity cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setProductRes(mapProductEntityToDto(cart.getProductEntity()));
        response.setQuantity(cart.getQuantity());
        response.setTotalPrice(cart.getQuantity() * cart.getProductEntity().getSellingPrice());
        return response;
    }

    private ProductRes mapProductEntityToDto(ProductEntity productEntity) {
        ProductRes productRes = new ProductRes();
        productRes.setId(productEntity.getId());
        productRes.setTitle(productEntity.getTitle());
        productRes.setDescription(productEntity.getDescription());
        productRes.setModelName(productEntity.getModelName());
        productRes.setItemCode(productEntity.getItemCode());
        productRes.setAvailable(productEntity.isAvailable());
        productRes.setInventoryStatus(productEntity.getInventoryStatus());
        productRes.setSellingPrice(productEntity.getSellingPrice());
        productRes.setProductImages(productEntity.getProductImages());
        productRes.setOriginalPrice(productEntity.getOriginalPrice());
        productRes.setHighlights(productEntity.getHighlights());
        productRes.setKeyFeatures(productEntity.getKeyFeatures());
        productRes.setCreatedDate(productEntity.getCreatedDate());
        productRes.setProductCategory(mapProductCategoryEntityToDto(productEntity.getProductCategory()));
        productRes.setProductType(mapProductTypeEntityToDto(productEntity.getProductType()));
        productRes.setSpecification(mapSpecificationEntityToDto(productEntity.getSpecificationEntity()));
        return productRes;
    }
}
