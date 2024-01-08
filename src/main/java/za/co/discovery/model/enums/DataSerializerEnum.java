package za.co.discovery.model.enums;

import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.hazelcast.serializers.CaptionSerializer;
import za.co.discovery.hazelcast.serializers.PrimaryImageSerializer;
import za.co.discovery.hazelcast.serializers.ReleaseDateSerializer;
import za.co.discovery.hazelcast.serializers.ReleaseYearSerializer;
import za.co.discovery.hazelcast.serializers.TitleClientResponseSerializer;
import za.co.discovery.hazelcast.serializers.TitleSerializer;
import za.co.discovery.hazelcast.serializers.TitleTextSerializer;
import za.co.discovery.hazelcast.serializers.TitleTypeSerializer;
import za.co.discovery.hazelcast.serializers.TitlesClientResponseSerializer;
import za.co.discovery.model.persistence.Caption;
import za.co.discovery.model.persistence.PrimaryImage;
import za.co.discovery.model.persistence.ReleaseDate;
import za.co.discovery.model.persistence.ReleaseYear;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.model.persistence.TitleText;
import za.co.discovery.model.persistence.TitleType;
import za.co.discovery.model.response.TitleClientResponse;
import za.co.discovery.model.response.TitlesClientResponse;

public enum DataSerializerEnum {

    CAPTION(Caption.class, 1, new CaptionSerializer()),
    PRIMARY_IMAGE(PrimaryImage.class, 2, new PrimaryImageSerializer()),
    RELEASE_DATE(ReleaseDate.class, 3, new ReleaseDateSerializer()),
    RELEASE_YEAR(ReleaseYear.class, 4, new ReleaseYearSerializer()),
    TITLE(Title.class, 5, new TitleSerializer()),
    TITLE_TEXT(TitleText.class, 6, new TitleTextSerializer()),
    TITLE_TYPE(TitleType.class, 7, new TitleTypeSerializer()),
    TITLES_CLIENT_RESPONSE(TitlesClientResponse.class, 8, new TitlesClientResponseSerializer()),
    TITLE_CLIENT_RESPONSE(TitleClientResponse.class, 9, new TitleClientResponseSerializer());

    private final Class<?> clazz;
    private final int typeId;
    private final StreamSerializer<?> serializer;

    DataSerializerEnum(final Class<?> clazz, final int typeId, final StreamSerializer<?> serializer) {
        this.clazz = clazz;
        this.typeId = typeId;
        this.serializer = serializer;
    }

    public static DataSerializerEnum retrieveDataSerializerEnum(final int typeId) {
        DataSerializerEnum retrievedDataSerializerEnum = null;
        for (DataSerializerEnum dataSerializerEnum : DataSerializerEnum.values()) {
            if (dataSerializerEnum.typeId == typeId) {
                retrievedDataSerializerEnum = dataSerializerEnum;
            }
        }
        if (null == retrievedDataSerializerEnum) {
            throw new IllegalArgumentException("The provided typeId could not be matched to a" +
                    " DataSerializerEnum");
        }
        return retrievedDataSerializerEnum;
    }

    public int getTypeId() {
        return typeId;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public StreamSerializer<?> getSerializer() {
        return serializer;
    }
}
