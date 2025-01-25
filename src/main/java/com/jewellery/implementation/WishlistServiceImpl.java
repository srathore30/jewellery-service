package com.jewellery.implementation;

import com.jewellery.dto.req.wishlist.WishlistReq;
import com.jewellery.dto.res.wishlist.WishlistRes;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.jewellery.constant.ApiErrorCodes;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final WishlistRepo wishlistRepo;

    @Override
    public WishlistRes createWishlist(WishlistReq wishlistReq) {
        ProductEntity product = productRepo.findById(wishlistReq.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(),ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));
        UserEntity user = userRepo.findById(wishlistReq.getUserId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(),ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));
        boolean exists = wishlistRepo.existsByUserEntityIdAndProductEntityId(user.getId(), product.getId());
        if (exists) {
            throw new ValidationException(ApiErrorCodes.PRODUCT_ALREADY_EXIST.getErrorCode(),ApiErrorCodes.PRODUCT_ALREADY_EXIST.getErrorMessage());
        }

        WishlistEntity wishlistEntity = mapDtoToEntity(wishlistReq);
        WishlistEntity savedWishList = wishlistRepo.save(wishlistEntity);
        return mapEntityToDto(savedWishList);
    }

    @Override
    public WishlistRes updateWishlist(Long id, Integer quantity) {
        WishlistEntity wishlistEntity = wishlistRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorCode(), ApiErrorCodes.WISHLIST_ITEM_NOT_FOUND.getErrorMessage()));

        wishlistEntity.setQuantity(quantity);

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
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WishlistEntity> wishlistPage = wishlistRepo.findAllByUserEntityId(userId, pageable);

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

    private WishlistEntity mapDtoToEntity(WishlistReq wishlistReq) {
        ProductEntity product = productRepo.findById(wishlistReq.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));
        UserEntity user = userRepo.findById(wishlistReq.getUserId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));
        WishlistEntity wishlistEntity = new WishlistEntity();
        wishlistEntity.setProductEntity(product);
        wishlistEntity.setUserEntity(user);
        wishlistEntity.setQuantity(wishlistReq.getQuantity());

        return wishlistEntity;
    }
}
