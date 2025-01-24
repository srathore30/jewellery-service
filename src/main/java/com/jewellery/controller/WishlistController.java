package com.jewellery.controller;

import com.jewellery.dto.req.WishlistReq;
import com.jewellery.dto.res.WishlistRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/create")
    public ResponseEntity<WishlistRes> createWishlist(@RequestBody WishlistReq wishlistReq) {
        WishlistRes reqs = wishlistService.createWishlist(wishlistReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(reqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistRes> getWishlistById(@PathVariable Long id ){
        WishlistRes resp = wishlistService.getWishlistById(id);
        return  ResponseEntity.status(HttpStatus.OK).body(resp);
    }
    @PutMapping("/{wishlistId}")
    public ResponseEntity<WishlistRes> updateWishlist(@Validated @RequestBody WishlistReq wishlistReq, @PathVariable Long wishlistId) {
        WishlistRes updatedTeam = wishlistService.updateWishlist(wishlistId, wishlistReq);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTeam);
    }


    @GetMapping("/allWishlist")
    public ResponseEntity<PaginatedResp<WishlistRes>> getAllWishlistByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam Long userId) {
        return ResponseEntity.ok(wishlistService.getAllWishlistByUserId(page, size, sortBy, sortDirection,userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamById(@PathVariable Long id) {
        wishlistService.deleteWishlistById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
