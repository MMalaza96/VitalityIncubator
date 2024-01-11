package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.model.persistence.TitleText;

import java.io.IOException;

public class TitleTextSerializer implements StreamSerializer<TitleText> {
    @Override
    public void write(ObjectDataOutput output, TitleText titleText) throws IOException {
        output.writeString(titleText.getText());
        output.writeString(titleText.getTypeName());
    }

    @Override
    public TitleText read(ObjectDataInput input) throws IOException {
        final String text = input.readString();
        final String typeName = input.readString();

        return new TitleText(text, typeName);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.TITLE_TEXT.getTypeId();
    }

    @Override
    public void destroy() {
        StreamSerializer.super.destroy();
    }
}
