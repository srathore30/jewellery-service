package com.jewellery.repositories;
import com.jewellery.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    Page<ProductEntity> findByProductCategoryId(Long categoryId, Pageable pageable);
    Page<ProductEntity> findByProductTypeId(Long productTypeId, Pageable pageable);


}
