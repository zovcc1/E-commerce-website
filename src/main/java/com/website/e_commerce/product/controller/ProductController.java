package com.website.e_commerce.product.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.website.e_commerce.exception.ProductNotFoundException;
import com.website.e_commerce.product.model.dto.ProductDto;
import com.website.e_commerce.product.model.dto.ProductResponse;
import com.website.e_commerce.product.model.mapper.ProductMapper;
import com.website.e_commerce.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService
                            ) {
        this.productService = productService;
    }
    @GetMapping("all")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        // Fetch the list of ProductDto objects from the service
        List<ProductDto> productDtos = productService.getAllProducts();

        // Convert the list of ProductDto objects to a list of ProductResponse objects
        List<ProductResponse> productResponses = ProductMapper.PRODUCT_MAPPER.dtosToProductResponses(productDtos);

        // Return the list of ProductResponse objects
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ProductResponse> getProductById(@RequestParam Long id) throws ProductNotFoundException{
      ProductDto productDto = productService.getProductById(id);
      ProductResponse response = ProductMapper.PRODUCT_MAPPER.dtoToProductResponse(productDto);
     return new ResponseEntity<>(response , HttpStatus.OK);

    }
    /** {@linkplain ProductService#createProduct(ProductDto)}*/
    @PostMapping("create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductDto dto) throws ProductNotFoundException{
            ProductResponse productResponse = productService.createProduct(dto);
            return new ResponseEntity<>(productResponse , HttpStatus.CREATED);

    }
    @PutMapping("update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductDto dto , @RequestParam Long id) throws ProductNotFoundException {

        ProductDto productDto = productService.updateProduct(dto,id);
        ProductResponse response = ProductMapper.PRODUCT_MAPPER.dtoToProductResponse(productDto);
        return new ResponseEntity<>(response , HttpStatus.OK);

    }
      @PutMapping("update/bulk")
      public ResponseEntity<List<ProductResponse>> updateProducts(@RequestBody List<ProductDto> dtos , @RequestParam List<Long> id) throws ProductNotFoundException{

            List<ProductDto> productDtos = productService.updateProducts(dtos,id);
            List<ProductResponse> response = ProductMapper.PRODUCT_MAPPER.dtosToProductResponses(productDtos);
            return new ResponseEntity<>(response , HttpStatus.OK);

      }
    @PatchMapping(path = "patch", consumes = "application/json")
    public ResponseEntity<ProductResponse> patchProduct(@RequestBody JsonPatch jsonPatch , @RequestParam Long id  ) throws ProductNotFoundException, JsonPatchException, IOException, JsonPatchException {
        ProductDto ProductPatched = productService.patchProduct(jsonPatch , id);
        ProductResponse response = ProductMapper.PRODUCT_MAPPER.dtoToProductResponse(ProductPatched);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * <p>{@link ProductService#deleteProduct(Long)}
     * delete product from {@code database}
     * returns @{@return }
     * </p>
     */
    @DeleteMapping("remove")
    public ResponseEntity<?> deleteProduct(@RequestParam Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (ProductNotFoundException e){

            throw new ProductNotFoundException();
        }

    }


}
