package za.co.discovery.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class PrimaryImageVO implements Serializable {

    private static final long serialVersionUID = 2825217977683931641L;

    private String id;

    private Integer width;

    private Integer height;

    private String url;

    private CaptionVO caption;

    @JsonProperty("__typename")
    private String typeName;
}