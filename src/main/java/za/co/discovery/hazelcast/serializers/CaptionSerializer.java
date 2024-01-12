package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.Caption;

import java.io.IOException;

public class CaptionSerializer implements StreamSerializer<Caption> {

    @Override
    public void write(ObjectDataOutput out, Caption caption) throws IOException {
        out.writeString(caption.getPlainText());
        out.writeString(caption.getTypeName());
    }

    @Override
    public Caption read(ObjectDataInput input) throws IOException {
        final String plainText = input.readString();
        final String typeName = input.readString();

        return new Caption(plainText,typeName);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.CAPTION.getTypeId();
    }

    @Override
    public void destroy() {
        StreamSerializer.super.destroy();
    }
}
