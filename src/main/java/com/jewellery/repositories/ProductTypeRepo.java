package com.jewellery.repositories;
import com.jewellery.entities.ProductTypeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductTypeEntity, Long> {
    Page<ProductTypeEntity> findAllByProductCategoryId(Long categoryId,Pageable pageable);

}
