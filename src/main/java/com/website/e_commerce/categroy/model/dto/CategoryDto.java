package com.website.e_commerce.categroy.model.dto;
import com.website.e_commerce.product.model.dto.ProductDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<ProductDto> products;
}
