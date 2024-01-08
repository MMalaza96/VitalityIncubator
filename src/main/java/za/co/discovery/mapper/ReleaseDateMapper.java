package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.ReleaseDate;
import za.co.discovery.vo.ReleaseDateVO;

@Mapper
public interface ReleaseDateMapper {

    ReleaseDateMapper INSTANCE = Mappers.getMapper(ReleaseDateMapper.class);

    @Mapping(target = "day", source = "releaseDate.day")
    @Mapping(target = "month", source = "releaseDate.month")
    @Mapping(target = "year", source = "releaseDate.year")
    @Mapping(target = "typeName", source = "typeName")
    ReleaseDateVO toVO(ReleaseDate releaseDate);

}
