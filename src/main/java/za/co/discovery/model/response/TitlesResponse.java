package za.co.discovery.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.discovery.vo.TitleVO;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TitlesResponse extends ApiResponse implements Serializable {

    private static final long serialVersionUID = -4584704729618211070L;

    private Integer page;

    private String next;

    private Integer entries;

    @JsonProperty(value = "results")
    private List<TitleVO> titles;
}
