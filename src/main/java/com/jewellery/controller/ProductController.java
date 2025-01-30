package com.jewellery.controller;

import com.jewellery.dto.req.product.ProductReq;
import com.jewellery.dto.res.product.ProductRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductRes> createProduct(@RequestBody ProductReq productReq) {
        ProductRes resp = productService.createProduct(productReq);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRes> getProductById(@PathVariable Long id ){
        ProductRes resp = productService.getProductById(id);
        return  new ResponseEntity<>(resp,HttpStatus.OK);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductRes> updateProduct(@RequestBody @Validated ProductReq productReq, @PathVariable Long productId) {
        ProductRes resp = productService.updateProduct(productId, productReq);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }


    @GetMapping("/getAllProducts")
    public ResponseEntity<PaginatedResp<ProductRes>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection){
        PaginatedResp<ProductRes> resp = productService.getAllProducts(page, size, sortBy, sortDirection);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @GetMapping("/getAllProductsByCategoryId")
    public ResponseEntity<PaginatedResp<ProductRes>> getAllProductsByCategoryId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam Long categoryId) {
        PaginatedResp<ProductRes> resp = productService.getAllProductsByCategoryId(page, size, sortBy, sortDirection,categoryId);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @GetMapping("/getAllProductsByProductTypeId")
    public ResponseEntity<PaginatedResp<ProductRes>> getAllProductsByProductTypeId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam Long productTypeId) {
        PaginatedResp<ProductRes> resp = productService.getAllProductsByProductTypeId(page, size, sortBy, sortDirection,productTypeId);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
