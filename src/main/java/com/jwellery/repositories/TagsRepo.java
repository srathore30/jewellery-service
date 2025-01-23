package com.jwellery.repositories;

import com.jwellery.entities.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepo extends JpaRepository<TagsEntity,Long> {
}
