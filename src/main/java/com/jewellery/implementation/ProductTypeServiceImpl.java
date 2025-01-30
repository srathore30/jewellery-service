package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.constant.Status;
import com.jewellery.dto.req.productType.ProductTypeReq;
import com.jewellery.dto.res.productType.ProductTypeRes;
import com.jewellery.dto.res.productcategory.ProductCategoryResponse;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.ProductCategoryEntity;
import com.jewellery.entities.ProductTypeEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.repositories.ProductCategoryRepo;
import com.jewellery.repositories.ProductTypeRepo;
import com.jewellery.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepo productTypeRepo;
    private final ProductCategoryRepo productCategoryRepo;

    @Override
    public ProductTypeRes createProductType(ProductTypeReq request) {
        ProductTypeEntity productTypeEntity = mapDtoToEntity(request);
        ProductTypeEntity savedEntity = productTypeRepo.save(productTypeEntity);

        return mapEntityToDto(savedEntity);
    }

    @Override
    public ProductTypeRes updateProductType(Long id, ProductTypeReq request) {
        ProductTypeEntity productTypeEntity = productTypeRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorMessage()));

        productTypeEntity.setName(request.getName());
        if (!Objects.equals(request.getImageUrl(), "")){
            productTypeEntity.setImageUrl((request.getImageUrl()));
        }
        productTypeEntity.setImageUrl(productTypeEntity.getImageUrl());
        return mapEntityToDto(productTypeRepo.save(productTypeEntity));
    }

    @Override
    public ProductTypeRes getProductTypeById(Long id) {
        ProductTypeEntity productTypeEntity = productTypeRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorMessage()));

        return mapEntityToDto(productTypeEntity);
    }

    @Override
    public PaginatedResp<ProductTypeRes> getAllProductTypes(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductTypeEntity> productTypePage = productTypeRepo.findAll(pageable);

        List<ProductTypeRes> responseList = productTypePage.getContent()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                productTypePage.getTotalElements(),
                productTypePage.getTotalPages(),
                page,
                responseList
        );
    }

    public PaginatedResp<ProductTypeRes> getAllByCategoryId( Long categoryId,int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductTypeEntity> productTypePage = productTypeRepo.findAllByProductCategoryId(categoryId,pageable);

        List<ProductTypeRes> responseList = productTypePage.getContent()
                .stream()
                .filter(productType -> productType.getStatus() != Status.INACTIVE)
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                productTypePage.getTotalElements(),
                productTypePage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public void deleteProductType(Long id) {
        ProductTypeEntity productTypeEntity = productTypeRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_TYPE_NOT_FOUND.getErrorMessage()));
        productTypeEntity.setStatus(Status.INACTIVE);
        productTypeRepo.save(productTypeEntity);
    }

    private ProductTypeRes mapEntityToDto(ProductTypeEntity entity) {
        ProductTypeRes response = new ProductTypeRes();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setImageUrl(entity.getImageUrl());
        response.setStatus(entity.getStatus());
        ProductCategoryEntity category = entity.getProductCategory();
        ProductCategoryResponse categoryResponse = new ProductCategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        response.setProductCategory(categoryResponse);
        return response;
    }

    private ProductTypeEntity mapDtoToEntity(ProductTypeReq request) {
        ProductCategoryEntity category = productCategoryRepo.findById(request.getProductCategoryId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.CATEGORY_NOT_FOUND.getErrorCode(), ApiErrorCodes.CATEGORY_NOT_FOUND.getErrorMessage()));
        ProductTypeEntity entity = new ProductTypeEntity();
        entity.setName(request.getName());
        entity.setStatus(Status.ACTIVE);
        entity.setImageUrl(request.getImageUrl());
        entity.setProductCategory(category);
        return entity;
    }

}
