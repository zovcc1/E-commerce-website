package com.website.e_commerce.product.model.mapper;

import com.website.e_commerce.product.model.dto.ProductDto;
import com.website.e_commerce.product.model.dto.ProductResponse;
import com.website.e_commerce.product.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    // Individual mappings
    ProductDto productToDto(Product product);

    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "unitCost", target = "unitCost")
    @Mapping(source = "availabilityStatus", target = "availabilityStatus")
    @Mapping(source = "rate", target = "rate")
    Product dtoToProduct(ProductDto dto);
    ProductResponse dtoToProductResponse(ProductDto dto);
    ProductDto productResponseToDto(ProductResponse response);
    ProductResponse productToProductResponse(Product product);
    Product ProductResponseToProduct(ProductResponse response);

    // List mappings
    List<ProductDto> productsToDtos(List<Product> products);
    List<Product> dtosToProducts(List<ProductDto> dtos);
    List<ProductResponse> dtosToProductResponses(List<ProductDto> dtos);
    List<ProductDto> productResponsesToDtos(List<ProductResponse> responses);
    List<ProductResponse> productsToProductsResponse(List<Product> product);
    List<Product> ProductsResponseToProducts(List<ProductResponse> responses);
}
