package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.constant.Status;
import com.jewellery.dto.req.productcategory.ProductCategoryRequest;
import com.jewellery.dto.res.productType.ProductTypeRes;
import com.jewellery.dto.res.productcategory.ProductCategoryResponse;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.ProductCategoryEntity;
import com.jewellery.entities.ProductTypeEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.repositories.ProductCategoryRepo;
import com.jewellery.service.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {


    private final ProductCategoryRepo productCategoryRepo;




    @Override
    public ProductCategoryResponse createProductCategory(ProductCategoryRequest request) {
        ProductCategoryEntity productCategoryEntity = mapDtoToEntity(request);
        ProductCategoryEntity savedEntity = productCategoryRepo.save(productCategoryEntity);

        return mapEntityToDto(savedEntity);
    }

    @Override
    public ProductCategoryResponse getProductCategoryById(Long id) {
        ProductCategoryEntity productCategoryEntity = productCategoryRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage()));

        return mapEntityToDto(productCategoryEntity);
    }

    @Override
    public PaginatedResp<ProductCategoryResponse> getAllProductCategory(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductCategoryEntity> productCategoryPage = productCategoryRepo.findAll(pageable);

        List<ProductCategoryResponse> responseList = productCategoryPage.getContent()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                productCategoryPage.getTotalElements(),
                productCategoryPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public ProductCategoryResponse updateProductCategory(Long id, ProductCategoryRequest request) {
        ProductCategoryEntity productCategoryEntity = productCategoryRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage()));

        productCategoryEntity.setName(request.getName());
        productCategoryEntity.setDescription(request.getDescription());

        if (!Objects.equals(request.getImageUrl(), "")){
            productCategoryEntity.setImageUrl((request.getImageUrl()));
        }
        productCategoryEntity.setImageUrl(productCategoryEntity.getImageUrl());
        return mapEntityToDto(productCategoryRepo.save(productCategoryEntity));
    }

    @Override
    public void deleteProductCategory(Long id) {

        ProductCategoryEntity productCategoryEntity = productCategoryRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage()));
        productCategoryEntity.setStatus(Status.INACTIVE);
        productCategoryRepo.save(productCategoryEntity);

    }


    private ProductCategoryEntity mapDtoToEntity(ProductCategoryRequest request) {
        ProductCategoryEntity entity = new ProductCategoryEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(Status.ACTIVE);
        entity.setImageUrl(request.getImageUrl());
        return entity;
    }

    private ProductCategoryResponse mapEntityToDto(ProductCategoryEntity entity) {
        ProductCategoryResponse response = new ProductCategoryResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setImageUrl(entity.getImageUrl());
        return response;
    }
}
