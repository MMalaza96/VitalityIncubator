package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.TitleType;
import za.co.discovery.vo.TitleTypeVO;

@Mapper
public interface TitleTypeMapper {

    TitleTypeMapper INSTANCE = Mappers.getMapper(TitleTypeMapper.class);

    @Mapping(target = "text", source = "titleType.text")
    @Mapping(target = "id", source = "titleType.id")
    @Mapping(target = "series", source = "titleType.series")
    @Mapping(target = "episode", source = "titleType.episode")
    @Mapping(target = "typeName", source = "titleType.typeName")
    TitleTypeVO toVO(TitleType titleType);

}
