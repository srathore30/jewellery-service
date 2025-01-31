package com.jewellery.service;

import com.jewellery.dto.req.specification.SpecificationReq;
import com.jewellery.dto.req.wishlist.WishlistReq;
import com.jewellery.dto.res.productType.ProductTypeRes;
import com.jewellery.dto.res.specification.SpecificationRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.dto.res.wishlist.WishlistRes;

import java.util.Optional;

public interface SpecificationService {
    SpecificationRes createSpecification(SpecificationReq specificationReq);
    SpecificationRes getSpecificationById(Long id);
    PaginatedResp<SpecificationRes> getAllSpecifications(int page, int size, String sortBy, String sortDirection);
   // SpecificationRes getSpecificationByProductId(Long productId);
    SpecificationRes updateSpecification(Long id, SpecificationReq specificationReq);

}
