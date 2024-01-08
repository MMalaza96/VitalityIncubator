package za.co.discovery.hazelcast.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.MapListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.discovery.model.enums.EntryListenerEnum;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static za.co.discovery.config.HazelcastConfiguration.CACHED_ITEMS;

@Component
public class Cache {

    private static Map<String, IMap<String, Object>> cacheMaps = new HashMap<>();
    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public Cache(final HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        for (Class<?> item : CACHED_ITEMS) {
            final String mapName = item.getSimpleName();
            final IMap<String, Object> cacheMap = hazelcastInstance.getMap(mapName);
            cacheMaps.put(mapName, cacheMap);
            cacheMap.addEntryListener(retrieveEntryListener(item), true);
        }
    }

    public static synchronized void cache(final String mapName,
                                          final String key,
                                          final Object value) {
        final IMap<String, Object> cacheMap = cacheMaps.get(mapName);
        cacheMap.put(key, value);
        cacheMaps.replace(mapName, cacheMap);
    }

    public static IMap<String, Object> retrieveCacheMap(final String mapName) {
        return cacheMaps.get(mapName);
    }

    private <T extends EntryAddedListener<String,Object>
            & EntryRemovedListener<String,Object>> T retrieveEntryListener(final Class<?> item) {
        final EntryListenerEnum entryListenerEnum = EntryListenerEnum
                .retrieveEntryListenerEnum(item);
        final Class<? extends MapListener> implementation = entryListenerEnum.getEntryListener();

        final Constructor<? extends MapListener> constructor;
        try {
            constructor = implementation.getConstructor(HazelcastInstance.class);
        } catch (final NoSuchMethodException exception) {
            throw new RuntimeException("Failed to retrieve constructor of " + implementation + ": "
                    + exception.getMessage(), exception);
        }

        final Object entryListener;
        try {
            entryListener = constructor.newInstance(hazelcastInstance);
        } catch (final InstantiationException |
                       IllegalAccessException |
                       InvocationTargetException exception) {
            throw new RuntimeException("Failed to create new instance from constructor " + constructor + ": "
                    + exception.getMessage(), exception);
        }
        return (T) implementation.cast(entryListener);
    }

}
