    package com.website.e_commerce.categroy.controller;

    import com.website.e_commerce.categroy.model.dto.CategoryDto;
    import com.website.e_commerce.categroy.service.CategoryService;
    import com.website.e_commerce.exception.AlreadyExistException;
    import com.website.e_commerce.exception.CategoryNotFoundException;
    import com.website.e_commerce.response.ApiResponse;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    import static org.springframework.http.HttpStatus.*;

    @Controller
    @RequestMapping("${api.prefix}/categories")
    public class CategoryController  {
    private final CategoryService categoryService;

        public CategoryController(CategoryService categoryService) {
            this.categoryService = categoryService;
        }

        @GetMapping
        public ResponseEntity<ApiResponse> getAllCategories() {
            try{
            List<CategoryDto> dto = categoryService.getAllCategories();


            return Optional.ofNullable(dto)
                .filter(list -> !list.isEmpty())
                    .map(list -> ResponseEntity.status(OK).body(new ApiResponse("found" , list)))
                    .orElseThrow(CategoryNotFoundException::new);




            }
            catch (CategoryNotFoundException e){
                 return ResponseEntity.status(404).body(new ApiResponse(e.getMessage() , null));
            }
        }

        @GetMapping("category")
        public ResponseEntity<ApiResponse> getCategoryById(@RequestParam Long id) throws CategoryNotFoundException{
           try {
               CategoryDto dtos = categoryService.getCategoryById(id);
               return ResponseEntity.ok(new ApiResponse("found:" , dtos));
           }
           catch (CategoryNotFoundException e){
               return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
           }

        }
        @GetMapping("category/{name}")
        public ResponseEntity<ApiResponse> getCategoryByName( @PathVariable String name) {
            try {
                CategoryDto dto = categoryService.getCategoryByName(name);
                return ResponseEntity.status(OK).body(new ApiResponse("found" , dto));
            }
            catch (CategoryNotFoundException e){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
            }
        }

        @PostMapping("category/create")
        public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDto dto) {
            try {
                CategoryDto categoryDto = categoryService.addCategory(dto);
                return ResponseEntity.status(CREATED).body(new ApiResponse("created" , categoryDto));
            }
            catch (AlreadyExistException e){
                return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage() , null));
            }
        }

        @PutMapping("/category/update")
        public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryDto dto, @RequestParam Long id) {
            try {
                CategoryDto categoryDto = categoryService.updateCategory(dto , id);
                return ResponseEntity.status(OK).body(new ApiResponse("updated successfully!" , categoryDto));
            }
            catch (CategoryNotFoundException e){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
            }

        }

        @DeleteMapping("/category/remove")
        public ResponseEntity<ApiResponse> deleteCategory(@RequestParam Long id) {
         try {
                categoryService.deleteCategory(id);
             return ResponseEntity.status(OK).body(new ApiResponse("deleted successfully" , null));
         }
         catch (CategoryNotFoundException e){
             return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
         }
        }
    }
