package com.jewellery.controller;

import com.jewellery.dto.req.product.ProductReq;
import com.jewellery.dto.req.specification.SpecificationReq;
import com.jewellery.dto.res.specification.SpecificationRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.dto.res.wishlist.WishlistRes;
import com.jewellery.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/specification")
@RequiredArgsConstructor
public class SpecificationController {
    private final SpecificationService specificationService;
    public ResponseEntity<SpecificationRes> createSpecification(@RequestBody SpecificationReq specificationReq) {
        SpecificationRes resp = specificationService.createSpecification(specificationReq);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecificationRes> getSpecificationById(@PathVariable Long id ){
        SpecificationRes resp = specificationService.getSpecificationById(id);
        return  new ResponseEntity<>(resp,HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<SpecificationRes> getSpecificationByProductId(@PathVariable Long productId ){
        SpecificationRes resp = specificationService.getSpecificationByProductId(productId);
        return  new ResponseEntity<>(resp,HttpStatus.OK);
    }
    @PutMapping("/{specificationId}")
    public ResponseEntity<SpecificationRes> updateSpecification(@RequestBody @Validated SpecificationReq specificationReq, @PathVariable Long specificationId) {
        SpecificationRes resp = specificationService.updateSpecification(specificationId, specificationReq);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

}
