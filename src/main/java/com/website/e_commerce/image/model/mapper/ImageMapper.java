package com.website.e_commerce.image.model.mapper;

import com.website.e_commerce.image.model.dto.ImageDto;
import com.website.e_commerce.image.model.entity.Image;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {

     ImageMapper IMAGE_MAPPER = Mappers.getMapper(ImageMapper.class);
    Image toEntity(ImageDto imageDto);

    Set<Image> toEntity(Set<ImageDto> imageDto);

    List<Image> toEntity(List<ImageDto> imageDto);

    Collection<Image> toEntity(Collection<ImageDto> imageDto);

    ImageDto toDto(Image image);

    Set<ImageDto> toDto(Set<Image> image);

    List<ImageDto> toDto(List<Image> image);

    Collection<ImageDto> toDto(Collection<Image> image);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Image partialUpdate(ImageDto imageDto, @MappingTarget Image image);
}