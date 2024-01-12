package za.co.discovery.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ReleaseDateVO {

    private Integer day;

    private Integer month;

    private Integer year;

    @JsonProperty("__typename")
    private String typeName;

}
