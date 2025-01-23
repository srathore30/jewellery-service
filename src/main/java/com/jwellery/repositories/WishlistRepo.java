package com.jwellery.repositories;

import com.jwellery.entities.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepo extends JpaRepository<WishlistEntity,Long> {
}
