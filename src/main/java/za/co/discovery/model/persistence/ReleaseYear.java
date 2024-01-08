package za.co.discovery.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ReleaseYear implements Serializable {

    private static final long serialVersionUID = -4874223825552689003L;

    private int year;

    private Integer endYear;

    @JsonProperty("__typename")
    private String typeName;

}
