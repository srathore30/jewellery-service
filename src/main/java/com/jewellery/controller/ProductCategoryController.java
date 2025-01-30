package com.jewellery.controller;

import com.jewellery.dto.req.productcategory.ProductCategoryRequest;
import com.jewellery.dto.res.productcategory.ProductCategoryResponse;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping("/create")
    public ResponseEntity<ProductCategoryResponse> createCategory(@RequestBody @Valid ProductCategoryRequest request) {
        ProductCategoryResponse response = productCategoryService.createProductCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getProductCategoryById(@PathVariable Long id) {
        ProductCategoryResponse response = productCategoryService.getProductCategoryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/allProductCategory")
    public ResponseEntity<PaginatedResp<ProductCategoryResponse>> getAllProductCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection){
        PaginatedResp<ProductCategoryResponse> response = productCategoryService.getAllProductCategory(page, size, sortBy, sortDirection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> updateProductCategory(
            @PathVariable Long id,
            @RequestBody ProductCategoryRequest request) {
        ProductCategoryResponse response = productCategoryService.updateProductCategory(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
