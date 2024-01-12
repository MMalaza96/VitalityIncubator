package za.co.discovery.hazelcast.serializers;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.persistence.PrimaryImage;
import za.co.discovery.model.persistence.ReleaseDate;
import za.co.discovery.model.persistence.ReleaseYear;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.model.persistence.TitleText;
import za.co.discovery.model.persistence.TitleType;

import java.io.IOException;

public class TitleSerializer implements StreamSerializer<Title> {
    @Override
    public void write(ObjectDataOutput output, Title title) throws IOException {
        output.writeString(title.get_id());
        output.writeString(title.getId());
        output.writeObject(title.getPrimaryImage());
        output.writeObject(title.getTitleType());
        output.writeObject(title.getTitleText());
        output.writeObject(title.getOriginalTitleText());
        output.writeObject(title.getReleaseYear());
        output.writeObject(title.getReleaseDate());
    }

    @Override
    public Title read(ObjectDataInput input) throws IOException {
        final String _id = input.readString();
        final String id = input.readString();
        final PrimaryImage primaryImage = input.readObject();
        final TitleType titleType = input.readObject();
        final TitleText titleText = input.readObject();
        final TitleText originalTitleText = input.readObject();
        final ReleaseYear releaseYear = input.readObject();
        final ReleaseDate releaseDate = input.readObject();

        return new Title(
                _id,
                id,
                primaryImage,
                titleType,
                titleText,
                originalTitleText,
                releaseYear,
                releaseDate);
    }

    @Override
    public int getTypeId() {
        return DataSerializerEnum.TITLE.getTypeId();
    }

    @Override
    public void destroy() {
        StreamSerializer.super.destroy();
    }
}
