package com.website.e_commerce.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.website.e_commerce.exception.ProductNotFoundException;
import com.website.e_commerce.product.model.dto.ProductDto;
import com.website.e_commerce.product.model.dto.ProductResponse;
import com.website.e_commerce.product.model.entity.Product;
import com.website.e_commerce.product.model.mapper.ProductMapper;
import com.website.e_commerce.product.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;

@Service
@Validated
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts(){
       List<Product> products = productRepository.findAll();
       if(products.isEmpty()){
           throw new ProductNotFoundException();
       }
       return ProductMapper.PRODUCT_MAPPER.productsToDtos(products);

  }
   public ProductDto getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
       return ProductMapper.PRODUCT_MAPPER.productToDto(product);
   }
   public ProductResponse createProduct(@Valid ProductDto dto){
       Product product = ProductMapper.PRODUCT_MAPPER.dtoToProduct(dto);
       product = productRepository.save(product);
       return ProductMapper.PRODUCT_MAPPER.productToProductResponse(product);
   }
    public void deleteProduct(Long id) {
       if(!productRepository.existsById(id))
           throw new ProductNotFoundException();
       productRepository.deleteById(id);
    }
   public ProductDto updateProduct(@Valid ProductDto dto , Long id ){
       // Fetch the product by ID, throw an exception if not found
       Product product = productRepository.findById(id)
               .orElseThrow(ProductNotFoundException::new);

       // Update the product entity with the new values from the DTO
       product.setProductName(dto.getProductName());
       product.setUnitCost(dto.getUnitCost());
       product.setAvailabilityStatus(dto.getAvailabilityStatus());
       product.setRate(dto.getRate());

       // Save the updated product entity
       Product updatedProduct = productRepository.save(product);

       // Convert the updated entity to a DTO and return it
       return ProductMapper.PRODUCT_MAPPER.productToDto(updatedProduct);
   }



    public List<ProductDto> updateProducts(List<ProductDto> dtos, List<Long> id) {
        List<Product> products = productRepository.findAllById(id);
        if (products.size() != dtos.size()) {
            throw new IllegalArgumentException("Mismatch between number of products and DTOs provided");
        }

       products.forEach(product -> {
           ProductDto matchingDto = dtos.stream().filter(dto->dto.getProductId().equals(product.getProductId()))
                   .findFirst()
                   .orElseThrow(()->new IllegalArgumentException("No matching DTO found for product with ID: " + product.getProductId()));

             product.setProductName(matchingDto.getProductName());
             product.setUnitCost(matchingDto.getUnitCost());
           product.setAvailabilityStatus(matchingDto.getAvailabilityStatus());
           product.setRate(matchingDto.getRate());
       });
        List<Product> updatedProducts = productRepository.saveAll(products);

        return ProductMapper.PRODUCT_MAPPER.productsToDtos(updatedProducts);


    }


    public ProductDto patchProduct(JsonPatch jsonPatch, Long id) throws IOException, JsonPatchException {
    //check if product exist in the repository
    Product existingProduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    // map Product to Dto
    ProductDto existingProductDto = ProductMapper.PRODUCT_MAPPER.productToDto(existingProduct);
    // apply patch to dto
    existingProductDto = applyPatchToCustomer(jsonPatch , existingProductDto);
    // return product dto
    return existingProductDto;
    }
    //method to apply patch operation on Product.
    private ProductDto applyPatchToCustomer(
            JsonPatch patch, ProductDto dto) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(dto, JsonNode.class));
        return objectMapper.treeToValue(patched, ProductDto.class);
    }

}
