package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.PrimaryImage;
import za.co.discovery.vo.PrimaryImageVO;

@Mapper
public interface PrimaryImageMapper {

    PrimaryImageMapper INSTANCE = Mappers.getMapper(PrimaryImageMapper.class);

    @Mapping(target = "id", source = "primaryImage.id")
    @Mapping(target = "width", source = "primaryImage.width")
    @Mapping(target = "height", source = "primaryImage.height")
    @Mapping(target = "url", source = "primaryImage.url")
    @Mapping(target = "caption",
            expression = "java(CaptionMapper.INSTANCE.toVO(primaryImage.getCaption()))")
    @Mapping(target = "typeName", source = "primaryImage.typeName")
    PrimaryImageVO toVO(PrimaryImage primaryImage);

}
