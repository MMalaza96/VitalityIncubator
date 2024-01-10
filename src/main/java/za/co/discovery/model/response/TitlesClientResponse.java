package za.co.discovery.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.discovery.model.persistence.Title;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TitlesClientResponse implements Serializable {

    private static final long serialVersionUID = -820706192608392888L;

    private Integer page;

    private String next;

    private Integer entries;

    @JsonProperty(value = "results")
    private List<Title> titles;
}
