package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.TitleText;
import za.co.discovery.vo.TitleTextVO;

@Mapper
public interface TitleTextMapper {

    TitleTextMapper INSTANCE = Mappers.getMapper(TitleTextMapper.class);

    @Mapping(target = "text", source = "titleText.text")
    @Mapping(target = "typeName", source = "titleText.typeName")
    TitleTextVO toVO(TitleText titleText);
}
