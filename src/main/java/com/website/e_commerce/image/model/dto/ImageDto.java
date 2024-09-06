package com.website.e_commerce.image.model.dto;

import lombok.Data;

/**
 * DTO for {@link com.website.e_commerce.image.model.entity.Image}
 */

@Data
public class ImageDto  {
    Long id;
    String fileName;
    String downloadUrl;
}