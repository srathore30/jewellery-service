package com.jewellery.controller;

import com.jewellery.dto.req.productType.ProductTypeReq;
import com.jewellery.dto.res.productType.ProductTypeRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-types")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PostMapping("/create")
    public ResponseEntity<ProductTypeRes> createProductType(@RequestBody @Validated ProductTypeReq productTypeReq) {
        ProductTypeRes response = productTypeService.createProductType(productTypeReq);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeRes> getProductTypeById(@PathVariable Long id) {
        ProductTypeRes response = productTypeService.getProductTypeById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductTypeRes> updateProductType(
            @PathVariable Long id,
            @RequestBody @Validated ProductTypeReq productTypeReq) {
        ProductTypeRes response = productTypeService.updateProductType(id, productTypeReq);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/allProductType")
    public ResponseEntity<PaginatedResp<ProductTypeRes>> getAllProductTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        PaginatedResp<ProductTypeRes> response = productTypeService.getAllProductTypes(page, size, sortBy, sortDirection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllByProductTypeCategoryId/{categoryId}")
    public ResponseEntity<PaginatedResp<ProductTypeRes>> getAllByProductTypeCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        PaginatedResp<ProductTypeRes> response = productTypeService.getAllByCategoryId(categoryId, page, size, sortBy, sortDirection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        productTypeService.deleteProductType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

