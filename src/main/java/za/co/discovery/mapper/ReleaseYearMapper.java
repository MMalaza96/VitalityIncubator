package za.co.discovery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import za.co.discovery.model.persistence.ReleaseYear;
import za.co.discovery.vo.ReleaseYearVO;

@Mapper
public interface ReleaseYearMapper {

    ReleaseYearMapper INSTANCE = Mappers.getMapper(ReleaseYearMapper.class);

    @Mapping(target = "year", source = "releaseYear.year")
    @Mapping(target = "endYear", source = "releaseYear.endYear")
    @Mapping(target = "typeName", source = "releaseYear.typeName")
    ReleaseYearVO toVO(ReleaseYear releaseYear);
}
