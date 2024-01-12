package za.co.discovery.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class CaptionVO implements Serializable {

    private static final long serialVersionUID = -8819624223998289777L;

    private String plainText;

    @JsonProperty("__typename")
    private String typeName;

}