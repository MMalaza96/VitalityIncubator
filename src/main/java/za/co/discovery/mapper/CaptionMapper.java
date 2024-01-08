package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.Caption;
import za.co.discovery.vo.CaptionVO;

@Mapper
public interface CaptionMapper {

    CaptionMapper INSTANCE = Mappers.getMapper(CaptionMapper.class);

    @Mapping(target = "plainText", source = "caption.plainText")
    @Mapping(target = "typeName", source = "caption.typeName")
    CaptionVO toVO(Caption caption);
}
