package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.model.response.TitlesClientResponse;
import za.co.discovery.model.response.TitlesResponse;
import za.co.discovery.vo.TitleVO;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TitlesClientResponseMapper {

    TitlesClientResponseMapper INSTANCE = Mappers.getMapper(TitlesClientResponseMapper.class);

    @Mapping(target = "page", source = "titlesClientResponse.page")
    @Mapping(target = "next", source = "titlesClientResponse.next")
    @Mapping(target = "entries", source = "titlesClientResponse.entries")
    @Mapping(target = "titles", source = "titles", qualifiedByName = "mapTitles")
    TitlesResponse toTitlesResponse(TitlesClientResponse titlesClientResponse);

    @Named("mapTitles")
    static List<TitleVO> mapTitles(final List<Title> titles) {
        return titles.stream()
                .map(TitleMapper.INSTANCE::toVO)
                .collect(Collectors.toList());
    }


}
