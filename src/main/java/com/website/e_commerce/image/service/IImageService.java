package com.website.e_commerce.image.service;

import com.website.e_commerce.image.model.dto.ImageDto;
import com.website.e_commerce.image.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files , Long ProductId);
    void updateImage(MultipartFile file , Long id) throws IOException, SQLException;

}
