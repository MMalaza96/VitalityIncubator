package za.co.discovery.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ReleaseDateVO implements Serializable {

    private static final long serialVersionUID = -5160288865625385793L;

    private Integer day;

    private Integer month;

    private Integer year;

    @JsonProperty("__typename")
    private String typeName;

}
