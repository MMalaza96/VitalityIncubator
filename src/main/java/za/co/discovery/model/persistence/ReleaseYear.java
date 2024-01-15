package za.co.discovery.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseYear {

    private static final long serialVersionUID = -4874223825552689003L;

    private Integer year;

    private Integer endYear;

    @JsonProperty("__typename")
    private String typeName;

}
