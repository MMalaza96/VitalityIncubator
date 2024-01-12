package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.Caption;
import za.co.discovery.model.persistence.PrimaryImage;

import java.io.IOException;
import java.util.Objects;

public class PrimaryImageSerializer implements StreamSerializer<PrimaryImage> {

    private static final String NULL = "null";

    @Override
    public void write(ObjectDataOutput output, PrimaryImage primaryImage) throws IOException {
        output.writeString(primaryImage.getId());
        output.writeString(String.valueOf(primaryImage.getWidth()));
        output.writeString(String.valueOf(primaryImage.getHeight()));
        output.writeString(primaryImage.getUrl());
        output.writeObject(primaryImage.getCaption());
        output.writeString(primaryImage.getTypeName());
    }

    @Override
    public PrimaryImage read(ObjectDataInput objectDataInput) throws IOException {

        String input = objectDataInput.readString();
        final String id = input;

        input = objectDataInput.readString();
        final Integer width = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        input = objectDataInput.readString();
        final Integer height = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        final String url = objectDataInput.readString();
        final Caption caption = objectDataInput.readObject();
        final String typeName = objectDataInput.readString();

        return new PrimaryImage(id, width, height, url, caption, typeName);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.PRIMARY_IMAGE.getTypeId();
    }

    @Override
    public void destroy() {
        StreamSerializer.super.destroy();
    }
}
