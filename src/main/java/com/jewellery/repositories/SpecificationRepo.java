package com.jewellery.repositories;

import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.SpecificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecificationRepo extends JpaRepository<SpecificationEntity,Long> {
    Optional<SpecificationEntity> findByProductId(Long productId);
}
