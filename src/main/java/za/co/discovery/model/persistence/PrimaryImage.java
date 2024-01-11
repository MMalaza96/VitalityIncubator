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
public class PrimaryImage implements Serializable {

    private static final long serialVersionUID = 2825217977683931641L;

    private String id;

    private Integer width;

    private Integer height;

    private String url;

    private Caption caption;

    @JsonProperty("__typename")
    private String typeName;
}