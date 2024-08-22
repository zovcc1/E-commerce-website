package com.website.e_commerce.product.model.dto;

import com.website.e_commerce.product.model.enums.AvailabilityStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private float unitCost;
    private AvailabilityStatus availabilityStatus;
    private int rate;

}
