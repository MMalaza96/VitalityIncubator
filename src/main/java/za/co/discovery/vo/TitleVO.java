package za.co.discovery.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class TitleVO implements Serializable {

    private static final long serialVersionUID = -3444478624860834429L;

    private String _id;

    private String id;

    private PrimaryImageVO primaryImage;

    private TitleTypeVO titleType;

    private TitleTextVO titleText;

    private TitleTextVO originalTitleText;

    private ReleaseYearVO releaseYear;

    private ReleaseDateVO releaseDate;
}
