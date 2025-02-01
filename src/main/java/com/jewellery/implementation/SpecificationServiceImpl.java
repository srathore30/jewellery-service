package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.dto.req.specification.SpecificationReq;
import com.jewellery.dto.res.product.ProductRes;
import com.jewellery.dto.res.specification.SpecificationRes;
import com.jewellery.dto.res.util.PaginatedResp;
import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.SpecificationEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.repositories.ProductRepo;
import com.jewellery.repositories.SpecificationRepo;
import com.jewellery.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecificationServiceImpl implements SpecificationService {
    private final ProductRepo productRepo;
    private final SpecificationRepo specificationRepo;

    @Override
    public SpecificationRes createSpecification(SpecificationReq specificationReq) {
        SpecificationEntity specificationEntity = mapDtoToEntity(specificationReq);
        SpecificationEntity savedSpecification = specificationRepo.save(specificationEntity);
        return mapEntityToDto(savedSpecification);
    }

    @Override
    public SpecificationRes updateSpecification(Long id, SpecificationReq specificationReq) {
        SpecificationEntity specificationEntity = specificationRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.SPECIFICATION_NOT_FOUND.getErrorCode(), ApiErrorCodes.SPECIFICATION_NOT_FOUND.getErrorMessage()));

        specificationEntity.setDesignType(specificationReq.getDesignType());
        specificationEntity.setSize(specificationReq.getSize());
        specificationEntity.setHallmark(specificationReq.isHallmark());
        specificationEntity.setWeight(specificationReq.getWeight());
        specificationEntity.setFinishType(specificationReq.getFinishType());
        specificationEntity.setSilverPurity(specificationReq.getSilverPurity());
        specificationEntity.setStoneType(specificationReq.getStoneType());
        return mapEntityToDto(specificationRepo.save(specificationEntity));
    }

    @Override
    public SpecificationRes getSpecificationById(Long id) {
        SpecificationEntity specificationEntity = specificationRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.SPECIFICATION_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.SPECIFICATION_NOT_FOUND.getErrorMessage()
                ));

        return mapEntityToDto(specificationEntity);
    }

    @Override
    public SpecificationRes getSpecificationByProductId(Long productId) {
        SpecificationEntity specificationEntity = specificationRepo.findByProductId(productId).
                orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.SPECIFICATION_NOT_FOUND.getErrorCode(),ApiErrorCodes.SPECIFICATION_NOT_FOUND.getErrorMessage()));
        return mapEntityToDto(specificationEntity);
    }

    private SpecificationRes mapEntityToDto(SpecificationEntity specificationEntity) {
        SpecificationRes specificationRes = new SpecificationRes();
        specificationRes.setId(specificationEntity.getId());
        specificationRes.setDesignType(specificationEntity.getDesignType());
        specificationRes.setSize(specificationEntity.getSize());
        specificationRes.setHallmark(specificationEntity.isHallmark());
        specificationRes.setFinishType(specificationEntity.getFinishType());
        specificationRes.setWeight(specificationEntity.getWeight());
        specificationRes.setSilverPurity(specificationEntity.getSilverPurity());
        specificationRes.setStoneType(specificationEntity.getStoneType());
        specificationRes.setProductRes(mapProductEntityToDto(specificationEntity.getProduct()));
        return specificationRes;
    }

    private SpecificationEntity mapDtoToEntity(SpecificationReq specificationReq) {
        ProductEntity product = productRepo.findById(specificationReq.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorCode(), ApiErrorCodes.PRODUCT_NOT_FOUND.getErrorMessage()));
        SpecificationEntity specificationEntity = new SpecificationEntity();
        specificationEntity.setDesignType(specificationReq.getDesignType());
        specificationEntity.setSize(specificationReq.getSize());
        specificationEntity.setProduct(product);
        specificationEntity.setHallmark(specificationReq.isHallmark());
        specificationEntity.setWeight(specificationReq.getWeight());
        specificationEntity.setFinishType(specificationReq.getFinishType());
        specificationEntity.setSilverPurity(specificationReq.getSilverPurity());
        specificationEntity.setStoneType(specificationReq.getStoneType());

        return specificationEntity;
    }

    private ProductRes mapProductEntityToDto(ProductEntity productEntity) {
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
        return productRes;
    }
}
