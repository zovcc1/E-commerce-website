package com.website.e_commerce.categroy.service;

import com.website.e_commerce.categroy.model.dto.CategoryDto;
import com.website.e_commerce.categroy.model.entity.Category;
import com.website.e_commerce.categroy.model.mapper.CategoryMapper;
import com.website.e_commerce.categroy.repository.CategoryRepository;
import com.website.e_commerce.exception.AlreadyExistException;
import com.website.e_commerce.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }


    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> category =  Optional.of(categoryRepository.findAll()).orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.CATEGORY_MAPPER.toDto(category);
     }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.CATEGORY_MAPPER.toDto(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name).orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.CATEGORY_MAPPER.toDto(category);
    }

    @Override
    public CategoryDto addCategory(CategoryDto dto) {

          Category savedCategory  = Optional.of(CategoryMapper.CATEGORY_MAPPER.toEntity(dto)).filter(category -> !categoryRepository.existsByNameAllIgnoreCase(category.getName()))
                  .map(categoryRepository::save).orElseThrow(()->new AlreadyExistException("category already exist"));

        return CategoryMapper.CATEGORY_MAPPER.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto, Long id) {
        Category category = Optional.of(CategoryMapper.CATEGORY_MAPPER.toEntity(dto)).filter(c -> !categoryRepository.existsByNameAllIgnoreCase(c.getName()))
                .map(categoryRepository::save).orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.CATEGORY_MAPPER.toDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,()->{
            throw  new CategoryNotFoundException();
        });
    }
}
