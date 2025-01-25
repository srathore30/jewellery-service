package com.jewellery.controller;

import com.jewellery.dto.req.wishlist.WishlistReq;
import com.jewellery.dto.res.wishlist.WishlistRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/create")
    public ResponseEntity<WishlistRes> createWishlist(@RequestBody WishlistReq wishlistReq) {
        WishlistRes resp = wishlistService.createWishlist(wishlistReq);
        return new ResponseEntity<>(resp,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistRes> getWishlistById(@PathVariable Long id ){
        WishlistRes resp = wishlistService.getWishlistById(id);
        return  new ResponseEntity<>(resp,HttpStatus.OK);
    }
    @PutMapping("/{wishlistId}")
    public ResponseEntity<WishlistRes> updateWishlist(@Validated @RequestParam Integer quantity, @PathVariable Long wishlistId) {
        WishlistRes resp = wishlistService.updateWishlist(wishlistId, quantity);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }


    @GetMapping("/getAllWishListItemsByUserId")
    public ResponseEntity<PaginatedResp<WishlistRes>> getAllWishlistByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam Long userId) {
        PaginatedResp<WishlistRes> resp = wishlistService.getAllWishlistByUserId(page, size, sortBy, sortDirection,userId);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamById(@PathVariable Long id) {
        wishlistService.deleteWishlistById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
