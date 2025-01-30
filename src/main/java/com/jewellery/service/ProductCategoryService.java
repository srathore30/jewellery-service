package com.jewellery.service;


import com.jewellery.dto.req.productcategory.ProductCategoryRequest;
import com.jewellery.dto.res.productcategory.ProductCategoryResponse;
import com.jewellery.dto.res.util.PaginatedResp;

import java.util.List;

public interface ProductCategoryService {

    ProductCategoryResponse createProductCategory(ProductCategoryRequest request);

    ProductCategoryResponse getProductCategoryById(Long id);

    PaginatedResp<ProductCategoryResponse> getAllProductCategory(int page, int size, String sortBy, String sortDirection);

    ProductCategoryResponse updateProductCategory(Long id, ProductCategoryRequest request);

    void deleteProductCategory(Long id);


}
