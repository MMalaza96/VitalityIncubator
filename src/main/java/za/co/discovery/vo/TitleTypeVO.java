package za.co.discovery.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class TitleTypeVO implements Serializable {

    private static final long serialVersionUID = 48000617931408468L;

    private String text;

    private String id;

    private boolean isSeries;

    private boolean isEpisode;

    @JsonProperty("__typename")
    private String typeName;
}
