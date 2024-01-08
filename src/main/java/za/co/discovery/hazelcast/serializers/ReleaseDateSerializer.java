package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.ReleaseDate;

import java.io.IOException;
import java.util.Objects;

public class ReleaseDateSerializer implements StreamSerializer<ReleaseDate> {

    private static final String NULL = "null";

    @Override
    public void write(ObjectDataOutput output, ReleaseDate releaseDate) throws IOException {
        output.writeString(String.valueOf(releaseDate.getDay()));
        output.writeString(String.valueOf(releaseDate.getMonth()));
        output.writeString(String.valueOf(releaseDate.getYear()));
        output.writeString(releaseDate.getTypeName());
    }

    @Override
    public ReleaseDate read(ObjectDataInput objectDataInput) throws IOException {

        String input = objectDataInput.readString();
        final Integer day = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        input = objectDataInput.readString();
        final Integer month = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        input = objectDataInput.readString();
        final Integer year = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        final String typeName = objectDataInput.readString();

        return new ReleaseDate(day, month, year, typeName);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.RELEASE_DATE.getTypeId();
    }

    @Override
    public void destroy() {
        StreamSerializer.super.destroy();
    }
}
