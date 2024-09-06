package com.website.e_commerce.image.service;

import com.website.e_commerce.exception.ResourceNotFoundException;
import com.website.e_commerce.image.model.dto.ImageDto;
import com.website.e_commerce.image.model.entity.Image;
import com.website.e_commerce.image.model.mapper.ImageMapper;
import com.website.e_commerce.image.repository.ImageRepository;
import com.website.e_commerce.product.model.dto.ProductDto;
import com.website.e_commerce.product.model.entity.Product;
import com.website.e_commerce.product.model.mapper.ProductMapper;
import com.website.e_commerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;
    private final ImageMapper imageMapper;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("image not found!"));
    }

    @Override
    public void deleteImageById(Long id) {
     imageRepository.findById(id).ifPresentOrElse(imageRepository::delete , ()->{
        throw  new ResourceNotFoundException("image not found");
    });

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long id) {
            ProductDto productDto = productService.getProductById(id);
            Product product = ProductMapper.PRODUCT_MAPPER.dtoToProduct(productDto);
            List<ImageDto> savedImageDto = new ArrayList<>();
            for (MultipartFile file : files){
                try{
                    Image image = new Image();
                    image.setFileName(file.getOriginalFilename());
                    image.setFileType(file.getContentType());
                    image.setImage(new SerialBlob(file.getBytes()));
                    image.setProduct(product);
                    String downloadUrl = apiPrefix + "/images/image/download/"+image.getId();
                    image.setDownloadUrl(downloadUrl);
                    Image savedImage = imageRepository.save(image);
                    savedImage.setDownloadUrl(apiPrefix + "/images/image/download/"+savedImage.getId());
                    imageRepository.save(image);
                    ImageDto imageDto = ImageMapper.IMAGE_MAPPER.toDto(savedImage);
                    savedImageDto.add(imageDto);
                }catch (IOException | SQLException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
             return savedImageDto;
            }

    @Override
    public void updateImage(MultipartFile file, Long id) {
               Image image = getImageById(id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}        
         