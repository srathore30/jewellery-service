package com.jewellery.service;

import com.jewellery.dto.req.product.ProductReq;
import com.jewellery.dto.res.product.ProductRes;
import com.jewellery.dto.req.wishlist.WishlistReq;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.dto.res.wishlist.WishlistRes;

public interface ProductService {
    ProductRes createProduct(ProductReq productReq);
    ProductRes getProductById(Long id);
    PaginatedResp<ProductRes> getAllProducts(int page, int size, String sortBy, String sortDirection);
    ProductRes updateProduct(Long id, ProductReq productReq);
    void deleteProductById(Long id);
    PaginatedResp<ProductRes> getAllProductsByCategoryId(int page, int size, String sortBy, String sortDirection,Long categoryId);
    PaginatedResp<ProductRes> getAllProductsByProductTypeId(int page, int size, String sortBy, String sortDirection,Long productTypeId);
}
