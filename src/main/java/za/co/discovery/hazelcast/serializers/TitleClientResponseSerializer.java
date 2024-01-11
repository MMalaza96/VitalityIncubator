package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.model.response.TitleClientResponse;

import java.io.IOException;

public class TitleClientResponseSerializer implements StreamSerializer<TitleClientResponse> {
    @Override
    public void write(ObjectDataOutput out, TitleClientResponse titleClientResponse) throws IOException {
        out.writeObject(titleClientResponse.getTitle());
    }

    @Override
    public TitleClientResponse read(ObjectDataInput input) throws IOException {
        final Title title = input.readObject();
        return new TitleClientResponse(title);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.TITLE_CLIENT_RESPONSE.getTypeId();
    }
}
