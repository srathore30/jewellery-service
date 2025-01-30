package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.constant.Status;
import com.jewellery.dto.req.product.ProductReq;
import com.jewellery.dto.res.product.ProductRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.*;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.repositories.*;
import com.jewellery.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductTypeRepo productTypeRepo;
    private final ProductCategoryRepo productCategoryRepo;

    @Override
    public ProductRes createProduct(ProductReq productReq) {
        ProductEntity productEntity = mapDtoToEntity(productReq);
        ProductEntity savedProduct = productRepo.save(productEntity);
        return mapEntityToDto(savedProduct);
    }

    @Override
    public ProductRes updateProduct(Long id, ProductReq productReq) {
        ProductEntity productEntity = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));

        productEntity.setTitle(productReq.getTitle());
        productEntity.setDescription(productReq.getDescription());
        productEntity.setModelName(productReq.getModelName());
        productEntity.setItemCode(productReq.getItemCode());
        productEntity.setIsAvailable(productReq.getIsAvailable());
        productEntity.setInventoryStatus(productReq.getInventoryStatus());
        productEntity.setOverAllRating(productReq.getOverAllRating());
        productEntity.setSellingPrice(productReq.getSellingPrice());
        productEntity.setProductImages(productReq.getProductImages());
        productEntity.setOriginalPrice(productReq.getOriginalPrice());
        productEntity.setHighlights(productReq.getHighlights());
        productEntity.setKeyFeatures(productReq.getKeyFeatures());


        return mapEntityToDto(productRepo.save(productEntity));
    }

    @Override
    public ProductRes getProductById(Long id) {
        ProductEntity productEntity = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(),ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));
        return mapEntityToDto(productEntity);
    }

    @Override
    public PaginatedResp<ProductRes> getAllProducts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductEntity> productPage = productRepo.findAll(pageable);

        List<ProductRes> responseList = productPage.getContent()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public PaginatedResp<ProductRes> getAllProductsByCategoryId(int page, int size, String sortBy, String sortDirection,Long categoryId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductEntity> productPage = productRepo.findByProductCategoryId(categoryId,pageable);

        List<ProductRes> responseList = productPage.getContent()
                .stream()
                .filter(product -> product.getStatus() != Status.INACTIVE)
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public PaginatedResp<ProductRes> getAllProductsByProductTypeId(int page, int size, String sortBy, String sortDirection,Long productTypeId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductEntity> productPage = productRepo.findByProductTypeId(productTypeId,pageable);

        List<ProductRes> responseList = productPage.getContent()
                .stream()
                .filter(product -> product.getStatus() != Status.INACTIVE)
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public void deleteProductById(Long id) {
        ProductEntity productEntity = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(),ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));
        productEntity.setStatus(Status.INACTIVE);
        productRepo.save(productEntity);
    }

    private ProductRes mapEntityToDto(ProductEntity productEntity) {
        ProductRes productRes = new ProductRes();
        productRes.setId(productEntity.getId());
        productRes.setTitle(productEntity.getTitle());
        productRes.setDescription(productEntity.getDescription());
        productRes.setModelName(productEntity.getModelName());
        productRes.setItemCode(productEntity.getItemCode());
        productRes.setIsAvailable(productEntity.getIsAvailable());
        productRes.setStatus(productEntity.getStatus());
        productRes.setInventoryStatus(productEntity.getInventoryStatus());
        productRes.setOverAllRating(productEntity.getOverAllRating());
        productRes.setSellingPrice(productEntity.getSellingPrice());
        productRes.setProductImages(productEntity.getProductImages());
        productRes.setOriginalPrice(productEntity.getOriginalPrice());
        productRes.setHighlights(productEntity.getHighlights());
        productRes.setKeyFeatures(productEntity.getKeyFeatures());
        productRes.setProductTypeName(productEntity.getProductType().getName());
        productRes.setProductCategoryName(productEntity.getProductCategory().getName());
        productRes.setCreatedDate(productEntity.getCreatedDate());

        return productRes;
    }

    private ProductEntity mapDtoToEntity(ProductReq productReq) {
        ProductCategoryEntity productCategory = productCategoryRepo.findById(productReq.getProductCategoryId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage()));
        ProductTypeEntity productType = productTypeRepo.findById(productReq.getProductTypeId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorMessage()));
        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle(productReq.getTitle());
        productEntity.setDescription(productReq.getDescription());
        productEntity.setProductCategory(productCategory);
        productEntity.setProductType(productType);
        productEntity.setHighlights(productReq.getHighlights());
        productEntity.setIsAvailable(productReq.getIsAvailable());
        productEntity.setInventoryStatus(productReq.getInventoryStatus());
        productEntity.setItemCode(productReq.getItemCode());
        productEntity.setKeyFeatures(productReq.getKeyFeatures());
        productEntity.setOriginalPrice(productReq.getOriginalPrice());
        productEntity.setModelName(productReq.getModelName());
        productEntity.setSellingPrice(productReq.getSellingPrice());
        productEntity.setOverAllRating(productReq.getOverAllRating());
        productEntity.setStatus(Status.ACTIVE);

        return productEntity;
    }
}
