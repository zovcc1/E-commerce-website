package com.website.e_commerce.categroy.repository;

import com.website.e_commerce.categroy.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {

   Optional<Category> findByNameIgnoreCase(String name);

   @Query("select (count(c) > 0) from Category c where upper(c.name) = upper(:name)")
   boolean existsByNameAllIgnoreCase(@Param("name") String name);


}
