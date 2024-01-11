package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.model.response.TitlesClientResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TitlesClientResponseSerializer implements StreamSerializer<TitlesClientResponse> {

    private static final String NULL = "null";

    @Override
    public void write(ObjectDataOutput out, TitlesClientResponse titlesClientResponse) throws IOException {
        out.writeString(String.valueOf(titlesClientResponse.getPage()));
        out.writeString(titlesClientResponse.getNext());
        out.writeString(String.valueOf(titlesClientResponse.getEntries()));

        final List<Title> titles = titlesClientResponse.getTitles();
        out.writeInt(titles.size());

        for (Title title : titles) {
            out.writeObject(title);
        }
    }

    @Override
    public TitlesClientResponse read(ObjectDataInput objectDataInput) throws IOException {

        String input = objectDataInput.readString();
        final Integer page = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        final String next = objectDataInput.readString();

        input = objectDataInput.readString();
        final Integer entries = Objects.equals(input, NULL) || input == null
                ? null : Integer.valueOf(input);

        final int size = objectDataInput.readInt(); //primitive because we never cache an empty title list
        final List<Title> titles = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            final Title title = objectDataInput.readObject();
            titles.add(title);
        }
        return new TitlesClientResponse(page, next, entries, titles);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.TITLES_CLIENT_RESPONSE.getTypeId();
    }
}
