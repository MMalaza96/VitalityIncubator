package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.response.TitleClientResponse;
import za.co.discovery.model.response.TitleResponse;

@Mapper
public interface TitleClientResponseMapper {

    TitleClientResponseMapper INSTANCE = Mappers.getMapper(TitleClientResponseMapper.class);

    @Mapping(target = "title", expression = "java(TitleMapper.INSTANCE.toVO(titleClientResponse.getTitle()))")
    TitleResponse toTitleResponse(TitleClientResponse titleClientResponse);

}
