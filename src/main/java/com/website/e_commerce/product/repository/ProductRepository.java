package com.website.e_commerce.product.repository;

import com.website.e_commerce.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

}
