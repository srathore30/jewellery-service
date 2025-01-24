package com.jewellery.implementation;

import com.jewellery.dto.req.WishlistReq;
import com.jewellery.dto.res.WishlistRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.UserEntity;
import com.jewellery.entities.WishlistEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.exception.ValidationException;
import com.jewellery.repositories.ProductRepo;
import com.jewellery.repositories.UserRepo;
import com.jewellery.repositories.WishlistRepo;
import com.jewellery.service.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.jewellery.constant.ApiErrorCodes;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final WishlistRepo wishlistRepo;

    public WishlistServiceImpl(WishlistRepo wishlistRepo,UserRepo userRepo, ProductRepo productRepo) {
        this.wishlistRepo = wishlistRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @Override
    public WishlistRes createWishlist(WishlistReq wishlistReq) {
        ProductEntity product = productRepo.findById(wishlistReq.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(),ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));
        UserEntity user = userRepo.findById(wishlistReq.getUserId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(),ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));
        boolean exists = wishlistRepo.existsByUserEntityAndProductEntity(user, product);
        if (exists) {
            throw new ValidationException(ApiErrorCodes.PRODUCT_ALREADY_EXIST.getErrorCode(),ApiErrorCodes.PRODUCT_ALREADY_EXIST.getErrorMessage());
        }

        WishlistEntity wishlistEntity = mapDtoToEntity(wishlistReq, product, user);
        WishlistEntity savedComment = wishlistRepo.save(wishlistEntity);
        return mapEntityToDto(savedComment);
    }

    public WishlistRes updateWishlist(Long id, WishlistReq wishlistReq) {
        WishlistEntity wishlistEntity = wishlistRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorCode(), ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorMessage()));

        UserEntity user = userRepo.findById(wishlistReq.getUserId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));

        ProductEntity product = productRepo.findById(wishlistReq.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));

        wishlistEntity.setUserEntity(user);
        wishlistEntity.setProductEntity(product);
        wishlistEntity.setQuantity(wishlistReq.getQuantity());

        return mapEntityToDto(wishlistRepo.save(wishlistEntity));
    }

    @Override
    public WishlistRes getWishlistById(Long id) {
        WishlistEntity wishlistEntity = wishlistRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorMessage()
                ));

        return mapEntityToDto(wishlistEntity);
    }

    @Override
    public PaginatedResp<WishlistRes> getAllWishlistByUserId(int page, int size, String sortBy, String sortDirection, Long userId) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WishlistEntity> wishlistPage = wishlistRepo.findAllByUserEntity(user, pageable);

        List<WishlistRes> responseList = wishlistPage.getContent()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                wishlistPage.getTotalElements(),
                wishlistPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public void deleteWishlistById(Long id) {
        WishlistEntity wishlistEntity = wishlistRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorCode(),ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorMessage()));
        wishlistRepo.delete(wishlistEntity);
    }

    private WishlistRes mapEntityToDto(WishlistEntity wishlistEntity) {
        WishlistRes wishlistRes = new WishlistRes();
        wishlistRes.setId(wishlistEntity.getId());
        wishlistRes.setProductId(wishlistEntity.getProductEntity().getId());
        wishlistRes.setUserId(wishlistEntity.getUserEntity().getId());
        wishlistRes.setUserName(wishlistEntity.getUserEntity().getName());
        wishlistRes.setProductName(wishlistEntity.getProductEntity().getModelName());
        wishlistRes.setQuantity(wishlistEntity.getQuantity());

        return wishlistRes;
    }

    private WishlistEntity mapDtoToEntity(WishlistReq wishlistReq, ProductEntity product, UserEntity user) {
        WishlistEntity wishlistEntity = new WishlistEntity();
        wishlistEntity.setProductEntity(product);
        wishlistEntity.setUserEntity(user);
        wishlistEntity.setQuantity(wishlistReq.getQuantity());

        return wishlistEntity;
    }
}
