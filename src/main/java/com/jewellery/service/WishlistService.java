package com.jewellery.service;

import com.jewellery.dto.req.WishlistReq;
import com.jewellery.dto.res.WishlistRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.WishlistEntity;

import java.util.List;

public interface WishlistService {
    WishlistRes createWishlist(WishlistReq wishlistReq);
    WishlistRes getWishlistById(Long id);
    PaginatedResp<WishlistRes> getAllWishlistByUserId(int page, int size, String sortBy, String sortDirection, Long userId);
    WishlistRes updateWishlist(Long id,WishlistReq wishlistReq);
    void deleteWishlistById(Long id);
}
