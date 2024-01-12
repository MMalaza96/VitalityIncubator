package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.TitleType;

import java.io.IOException;
import java.util.Objects;

public class TitleTypeSerializer implements StreamSerializer<TitleType> {

    private static final String NULL = "null";

    @Override
    public void write(ObjectDataOutput output, TitleType titleType) throws IOException {
        output.writeString(titleType.getText());
        output.writeString(titleType.getId());
        output.writeString(String.valueOf(titleType.getIsSeries()));
        output.writeString(String.valueOf(titleType.getIsEpisode()));
        output.writeString(titleType.getTypeName());
    }

    @Override
    public TitleType read(ObjectDataInput objectDataInput) throws IOException {
        final String text = objectDataInput.readString();
        final String id = objectDataInput.readString();

        String input = objectDataInput.readString();
        final Boolean isSeries = Objects.equals(input, NULL) || input == null
                ? null : Boolean.valueOf(input);

        input = objectDataInput.readString();
        final Boolean isEpisode = Objects.equals(input, NULL) || input == null
                ? null : Boolean.valueOf(input);

        final String typeName = objectDataInput.readString();

        return new TitleType(text, id, isSeries, isEpisode, typeName);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.TITLE_TYPE.getTypeId();
    }

    @Override
    public void destroy() {
        StreamSerializer.super.destroy();
    }
}
