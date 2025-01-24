package com.jewellery.repositories;

import com.jewellery.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserEntityIdAndProductEntityId(Long userId, Long productId);
}
