package com.website.e_commerce.categroy.model.mapper;

import com.website.e_commerce.categroy.model.dto.CategoryDto;
import com.website.e_commerce.categroy.model.entity.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
public interface CategoryMapper {
CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryDto categoryDto);

    @AfterMapping
    default void linkProducts(@MappingTarget Category category) {
        category.getProducts().forEach(product -> product.setCategory(category));
    }

    Set<Category> toEntity(Set<CategoryDto> categoryDto);

    List<Category> toEntity(List<CategoryDto> categoryDto);

    Collection<Category> toEntity(Collection<CategoryDto> categoryDto);

    CategoryDto toDto(Category category);

    Set<CategoryDto> toDto(Set<Category> category);

    List<CategoryDto> toDto(List<Category> category);

    Collection<CategoryDto> toDto(Collection<Category> category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Category partialUpdate(CategoryDto categoryDto, @MappingTarget Category category);
}
