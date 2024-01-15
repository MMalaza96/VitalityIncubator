package za.co.discovery.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.discovery.vo.TitleVO;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TitleResponse extends ApiResponse implements Serializable {

    private static final long serialVersionUID = -4584704729618211070L;

    @JsonProperty(value = "results")
    private TitleVO title;
}
