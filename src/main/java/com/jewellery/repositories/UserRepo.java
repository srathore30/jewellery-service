package com.jewellery.repositories;

import com.jewellery.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByMobileNo(Long mobile);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByMobileNoAndPassword(Long mobileNo,String password);

}
