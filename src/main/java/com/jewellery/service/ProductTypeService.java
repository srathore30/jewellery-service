package com.jewellery.service;

import com.jewellery.dto.req.productType.ProductTypeReq;
import com.jewellery.dto.res.productType.ProductTypeRes;
import com.jewellery.dto.res.util.PaginatedResp;

import java.util.List;

public interface ProductTypeService {
    ProductTypeRes createProductType(ProductTypeReq request);

    ProductTypeRes getProductTypeById(Long id);

    PaginatedResp<ProductTypeRes> getAllProductTypes(int page, int size, String sortBy, String sortDirection);

    ProductTypeRes updateProductType(Long id, ProductTypeReq request);

    void deleteProductType(Long id);

    PaginatedResp<ProductTypeRes> getAllByCategoryId(Long categoryId,int page, int size, String sortBy, String sortDirection);
}
