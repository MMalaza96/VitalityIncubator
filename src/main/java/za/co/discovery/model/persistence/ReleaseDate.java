package za.co.discovery.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ReleaseDate {

    private Integer day;

    private Integer month;

    private Integer year;

    @JsonProperty("__typename")
    private String typeName;

}
