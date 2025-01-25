package com.jewellery.service;

import com.jewellery.dto.req.wishlist.WishlistReq;
import com.jewellery.dto.res.wishlist.WishlistRes;
import com.jewellery.dto.res.util.PaginatedResp;

public interface WishlistService {
    WishlistRes createWishlist(WishlistReq wishlistReq);
    WishlistRes getWishlistById(Long id);
    PaginatedResp<WishlistRes> getAllWishlistByUserId(int page, int size, String sortBy, String sortDirection, Long userId);
    WishlistRes updateWishlist(Long id, Integer quantity);
    void deleteWishlistById(Long id);
}
