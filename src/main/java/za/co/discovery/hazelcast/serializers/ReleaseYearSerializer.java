package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.ReleaseYear;

import java.io.IOException;
import java.util.Objects;

public class ReleaseYearSerializer implements StreamSerializer<ReleaseYear> {

    private static final String NULL = "null";

    @Override
    public void write(ObjectDataOutput output, ReleaseYear releaseYear) throws IOException {
        output.writeString(String.valueOf(releaseYear.getYear()));
        output.writeString(String.valueOf(releaseYear.getEndYear()));
        output.writeString(releaseYear.getTypeName());
    }

    @Override
    public ReleaseYear read(ObjectDataInput objectDataInput) throws IOException {

        String input = objectDataInput.readString();
        final Integer year = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        input = objectDataInput.readString();
        final Integer endYear = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        final String typeName = objectDataInput.readString();

        return new ReleaseYear(year, endYear, typeName);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.RELEASE_YEAR.getTypeId();
    }

    @Override
    public void destroy() {
        StreamSerializer.super.destroy();
    }
}
