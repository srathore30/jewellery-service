package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.constant.Status;
import com.jewellery.dto.req.product.ProductReq;
import com.jewellery.dto.req.product.UpdateProductImagesReq;
import com.jewellery.dto.req.specification.SpecificationReq;
import com.jewellery.dto.res.product.ProductRes;
import com.jewellery.dto.res.productType.ProductTypeRes;
import com.jewellery.dto.res.productcategory.ProductCategoryResponse;
import com.jewellery.dto.res.specification.SpecificationRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.*;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.repositories.*;
import com.jewellery.service.ProductService;
import com.jewellery.service.SpecificationService;
import com.jewellery.util.ImageUploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.jewellery.util.CodeGenerator.generateItemCode;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductTypeRepo productTypeRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final ImageUploader imageUploader;
    private  final SpecificationService specificationService;

    @Override
    @Transactional
    public ProductRes createProduct(ProductReq productReq) {
        ProductEntity productEntity = mapDtoToEntity(productReq);
        ProductEntity savedProduct = productRepo.save(productEntity);

        if (productReq.getSpecificationReq() != null) {
            SpecificationReq specificationReq = productReq.getSpecificationReq();
            specificationReq.setProductId(savedProduct.getId());
            specificationService.createSpecification(specificationReq);
        }
        if (!CollectionUtils.isEmpty(productReq.getTags())) {
            savedProduct.setTags(productReq.getTags().stream()
                    .map(tag -> new TagsEntity(savedProduct, tag))
                    .collect(Collectors.toList()));
        }
        return mapEntityToDto(savedProduct);
    }

    @Override
    public ProductRes updateProduct(Long id, ProductReq productReq) {
        ProductEntity productEntity = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));

        productEntity.setTitle(productReq.getTitle());
        productEntity.setDescription(productReq.getDescription());
        productEntity.setModelName(productReq.getModelName());
        productEntity.setAvailable(productReq.isAvailable());
        productEntity.setInventoryStatus(productReq.getInventoryStatus());
        productEntity.setSellingPrice(productReq.getSellingPrice());
        productEntity.setOriginalPrice(productReq.getOriginalPrice());
        productEntity.setHighlights(productReq.getHighlights());
        productEntity.setKeyFeatures(productReq.getKeyFeatures());
        if (productReq.getUpdateProductImagesReq() != null) {
            updateProductImages(productEntity, productReq.getUpdateProductImagesReq());
        }
        if (productReq.getSpecificationReq() != null) {
            specificationService.updateSpecification(id, productReq.getSpecificationReq());
        }
        if (productReq.getTags() != null) {
            List<TagsEntity> existingTags = productEntity.getTags();
            List<String> newTags = productReq.getTags();
            existingTags.removeIf(tag -> !newTags.contains(tag.getTag()));
            for (String tag : newTags) {
                if (existingTags.stream().noneMatch(t -> t.getTag().equals(tag))) {
                    existingTags.add(new TagsEntity(productEntity, tag));
                }
            }
            productEntity.setTags(existingTags);
        }



        return mapEntityToDto(productRepo.save(productEntity));
    }

    private void updateProductImages(ProductEntity productEntity, UpdateProductImagesReq updateProductImagesReq) {
        List<String> existingImages = productEntity.getProductImages();
        if ( !CollectionUtils.isEmpty(updateProductImagesReq.getOldImagesList())) {
            for (String oldImage : updateProductImagesReq.getOldImagesList()) {
                existingImages.remove(oldImage);
            }
        }
        if (!CollectionUtils.isEmpty(updateProductImagesReq.getNewImagesList())) {
            List<String> uploadedImages = updateProductImagesReq.getNewImagesList().stream()
                    .map(imageUploader::uploadFile)
                    .collect(Collectors.toList());
            existingImages.addAll(uploadedImages);
        }
        productEntity.setProductImages(existingImages);
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
        productRes.setAvailable(productEntity.isAvailable());
        productRes.setStatus(productEntity.getStatus());
        productRes.setInventoryStatus(productEntity.getInventoryStatus());
        productRes.setSellingPrice(productEntity.getSellingPrice());
        productRes.setProductImages(productEntity.getProductImages());
        productRes.setOriginalPrice(productEntity.getOriginalPrice());
        productRes.setHighlights(productEntity.getHighlights());
        productRes.setKeyFeatures(productEntity.getKeyFeatures());
        productRes.setCreatedDate(productEntity.getCreatedDate());
        productRes.setProductCategory(mapProductCategoryEntityToDto(productEntity.getProductCategory()));
        productRes.setProductType(mapProductTypeEntityToDto(productEntity.getProductType()));
        productRes.setSpecification(mapSpecificationEntityToDto(productEntity.getSpecificationEntity()));

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
        productEntity.setAvailable(productReq.isAvailable());
        productEntity.setInventoryStatus(productReq.getInventoryStatus());
        productEntity.setItemCode(generateItemCode());
        productEntity.setKeyFeatures(productReq.getKeyFeatures());
        productEntity.setOriginalPrice(productReq.getOriginalPrice());
        productEntity.setModelName(productReq.getModelName());
        productEntity.setSellingPrice(productReq.getSellingPrice());
        productEntity.setStatus(Status.ACTIVE);
        if (productReq.getUpdateProductImagesReq() != null && productReq.getUpdateProductImagesReq().getNewImagesList() != null) {
            List<String> uploadedImages = productReq.getUpdateProductImagesReq().getNewImagesList().stream()
                    .map(imageUploader::uploadFile)
                    .collect(Collectors.toList());
            productEntity.setProductImages(uploadedImages);
        }

        if (!CollectionUtils.isEmpty(productReq.getTags())) {
            productEntity.setTags(productReq.getTags().stream()
                    .map(tag -> new TagsEntity(productEntity, tag))
                    .collect(Collectors.toList()));
        }

        return productEntity;
    }

    public static ProductTypeRes mapProductTypeEntityToDto(ProductTypeEntity entity) {
        ProductTypeRes response = new ProductTypeRes();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setImageUrl(entity.getImageUrl());
        response.setStatus(entity.getStatus());
        return response;
    }

    public static ProductCategoryResponse mapProductCategoryEntityToDto(ProductCategoryEntity entity) {
        ProductCategoryResponse response = new ProductCategoryResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setImageUrl(entity.getImageUrl());
        return response;
    }

        public static SpecificationRes mapSpecificationEntityToDto(SpecificationEntity specificationEntity) {
        SpecificationRes specificationRes = new SpecificationRes();
        specificationRes.setId(specificationEntity.getId());
        specificationRes.setDesignType(specificationEntity.getDesignType());
        specificationRes.setSize(specificationEntity.getSize());
        specificationRes.setHallmark(specificationEntity.isHallmark());
        specificationRes.setFinishType(specificationEntity.getFinishType());
        specificationRes.setWeight(specificationEntity.getWeight());
        specificationRes.setSilverPurity(specificationEntity.getSilverPurity());
        specificationRes.setStoneType(specificationEntity.getStoneType());
        return specificationRes;
    }

}
