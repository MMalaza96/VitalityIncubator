package za.co.discovery.model.persistence;

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
public class Title implements Serializable {

    private static final long serialVersionUID = -3444478624860834429L;

    private String _id;

    private String id;

    private PrimaryImage primaryImage;

    private TitleType titleType;

    private TitleText titleText;

    private TitleText originalTitleText;

    private ReleaseYear releaseYear;

    private ReleaseDate releaseDate;
}
