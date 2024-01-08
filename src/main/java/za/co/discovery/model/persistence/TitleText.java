package za.co.discovery.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class TitleText implements Serializable {

    private static final long serialVersionUID = 1875405057406907296L;

    private String text;

    @JsonProperty("__typename")
    private String typeName;

}
