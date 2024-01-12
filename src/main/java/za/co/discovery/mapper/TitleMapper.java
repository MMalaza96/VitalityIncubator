package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.vo.TitleVO;

@Mapper
public interface TitleMapper {

    TitleMapper INSTANCE = Mappers.getMapper(TitleMapper.class);

    @Mapping(target = "_id", source = "title._id")
    @Mapping(target = "id", source = "title.id")
    @Mapping(target = "primaryImage",
            expression = "java(PrimaryImageMapper.INSTANCE.toVO(title.getPrimaryImage()))")
    @Mapping(target = "titleType",
            expression = "java(TitleTypeMapper.INSTANCE.toVO(title.getTitleType()))")
    @Mapping(target = "titleText",
            expression = "java(TitleTextMapper.INSTANCE.toVO(title.getTitleText()))")
    @Mapping(target = "originalTitleText",
            expression = "java(TitleTextMapper.INSTANCE.toVO(title.getOriginalTitleText()))")
    @Mapping(target = "releaseYear",
            expression = "java(ReleaseYearMapper.INSTANCE.toVO(title.getReleaseYear()))")
    @Mapping(target = "releaseDate",
            expression = "java(ReleaseDateMapper.INSTANCE.toVO(title.getReleaseDate()))")
    TitleVO toVO(Title title);

}
