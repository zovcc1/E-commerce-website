package com.website.e_commerce.categroy.service;

import com.website.e_commerce.categroy.model.dto.CategoryDto;

import java.util.List;


public interface ICategoryService {

 public List<CategoryDto> getAllCategories();
 public CategoryDto getCategoryById(Long id);
 public CategoryDto getCategoryByName(String name);
 public CategoryDto addCategory(CategoryDto dto);
 public CategoryDto updateCategory(CategoryDto dto , Long id);
 public void deleteCategory(Long id);

}
