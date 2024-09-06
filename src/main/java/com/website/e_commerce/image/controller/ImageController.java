package com.website.e_commerce.image.controller;

import com.website.e_commerce.exception.ResourceNotFoundException;
import com.website.e_commerce.image.model.dto.ImageDto;
import com.website.e_commerce.image.model.entity.Image;
import com.website.e_commerce.image.model.mapper.ImageMapper;
import com.website.e_commerce.image.service.ImageService;
import com.website.e_commerce.product.service.ProductService;
import com.website.e_commerce.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final ProductService productService;
    private final ImageMapper imageMapper;

    @PostMapping("upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", e.getMessage()));
        }

    }

    @GetMapping("image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + image.getFileName() + "\"").body(resource);

    }

    @PutMapping("image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestBody MultipartFile file, @PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image == null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("image updated successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", INTERNAL_SERVER_ERROR));


    }

    @DeleteMapping("image/{imageId}/delete")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image == null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("image deleted successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("delete failed", INTERNAL_SERVER_ERROR));


    }

}
