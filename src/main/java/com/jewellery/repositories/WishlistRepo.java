package com.jewellery.repositories;

import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.UserEntity;
import com.jewellery.entities.WishlistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepo extends JpaRepository<WishlistEntity,Long> {
    boolean existsByUserEntityIdAndProductEntityId(Long userId, Long productId);
    Page<WishlistEntity> findAllByUserEntityId(Long userId, Pageable pageable);

}
