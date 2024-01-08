package za.co.discovery.model.enums;

import com.hazelcast.map.listener.MapListener;
import za.co.discovery.hazelcast.listeners.TitleClientResponseEntryListener;
import za.co.discovery.hazelcast.listeners.TitlesClientResponseEntryListener;
import za.co.discovery.model.response.TitleClientResponse;
import za.co.discovery.model.response.TitlesClientResponse;

import java.io.Serializable;

public enum EntryListenerEnum {

    TITLES_CLIENT_RESPONSE(TitlesClientResponse.class, TitlesClientResponseEntryListener.class),
    TITLE_CLIENT_RESPONSE(TitleClientResponse.class, TitleClientResponseEntryListener.class);

    private final Class<? extends Serializable> clazz;
    private final Class<? extends MapListener> entryListener;

    EntryListenerEnum(final Class<? extends Serializable> clazz,
                      final Class<? extends MapListener> entryListener) {
        this.clazz = clazz;
        this.entryListener = entryListener;
    }

    public static EntryListenerEnum retrieveEntryListenerEnum(final Class<?> clazz) {
        EntryListenerEnum retrievedEntryListenerEnum = null;
        for (EntryListenerEnum entryListenerEnum : EntryListenerEnum.values()) {
            if (entryListenerEnum.clazz.equals(clazz)) {
                retrievedEntryListenerEnum = entryListenerEnum;
            }
        }
        if (null == retrievedEntryListenerEnum) {
            throw new IllegalArgumentException("The provided class " + clazz
                    + " could not be matched to a EntryListenerEnum");
        }
        return retrievedEntryListenerEnum;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Class<? extends MapListener> getEntryListener() {
        return entryListener;
    }
}
