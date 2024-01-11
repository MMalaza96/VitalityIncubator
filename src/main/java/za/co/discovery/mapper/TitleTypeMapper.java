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
    @Mapping(target = "isSeries", source = "titleType.isSeries")
    @Mapping(target = "isEpisode", source = "titleType.isEpisode")
    @Mapping(target = "typeName", source = "titleType.typeName")
    TitleTypeVO toVO(TitleType titleType);

}
