package za.co.discovery.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.discovery.model.persistence.Title;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TitleClientResponse implements Serializable{

    private static final long serialVersionUID = -4584704729618211070L;

    @JsonProperty(value = "results")
    private Title title;

}
