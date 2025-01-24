package com.jewellery.repositories;

import com.jewellery.entities.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserEntityIdAndProductEntityId(Long userId, Long productId);
    Page<CartEntity> findByUserEntityId(Long userId, Pageable pageable);
}
