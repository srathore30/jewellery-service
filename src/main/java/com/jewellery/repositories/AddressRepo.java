package com.jewellery.repositories;

import com.jewellery.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AddressRepo extends JpaRepository<AddressEntity,Long> {

    List<AddressEntity> findByUserEntityId(Long userId);

    @Query("SELECT a FROM AddressEntity a WHERE a.userEntity.id = :userId AND a.isActive = true")
    Optional<AddressEntity> findActiveAddressByUserId(@Param("userId") Long userId);
}
