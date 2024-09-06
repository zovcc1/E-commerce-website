package com.website.e_commerce.image.repository;

import com.website.e_commerce.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    boolean existsById(Long id);

    
}