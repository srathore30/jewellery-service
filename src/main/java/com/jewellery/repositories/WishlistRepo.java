package com.jewellery.repositories;

import com.jewellery.entities.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepo extends JpaRepository<WishlistEntity,Long> {
}
