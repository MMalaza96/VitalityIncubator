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
public class TitleType implements Serializable {

    private static final long serialVersionUID = 48000617931408468L;

    private String text;

    private String id;

    private Boolean isSeries;

    private Boolean isEpisode;

    @JsonProperty("__typename")
    private String typeName;
}
