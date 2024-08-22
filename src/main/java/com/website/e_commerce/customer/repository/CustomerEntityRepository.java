package com.website.e_commerce.customer.repository;

import com.website.e_commerce.customer.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity , Long> {
    Optional<CustomerEntity> findByEmail(String email);
}
