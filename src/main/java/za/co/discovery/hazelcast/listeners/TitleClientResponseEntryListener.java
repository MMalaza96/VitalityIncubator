package za.co.discovery.hazelcast.listeners;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import lombok.extern.slf4j.Slf4j;
import za.co.discovery.model.response.TitleClientResponse;

import java.util.UUID;

@Slf4j
public class TitleClientResponseEntryListener implements
        EntryAddedListener<String, TitleClientResponse>,
        EntryRemovedListener<String, TitleClientResponse> {

    private final HazelcastInstance hazelcastInstance;

    public TitleClientResponseEntryListener(final HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public void entryAdded(final EntryEvent<String, TitleClientResponse> entryEvent) {
        try {
            final String key = entryEvent.getKey();
            if (isRemoteModification(entryEvent)) {
                final String address = entryEvent.getMember().getAddress().toString();
                log.debug("Remote cache entry added map event has occurred for key: " + key);
                log.debug("Remote address: " + address);
            } else {
                log.debug("Local cache entry added map event has occurred for key: " + key);
            }
        } catch (final Exception exception) {
            log.error("Failure occurred in entryAdded listener: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void entryRemoved(final EntryEvent<String, TitleClientResponse> entryEvent) {
        try {
            final String key = entryEvent.getKey();
            if (isRemoteModification(entryEvent)) {
                final String address = entryEvent.getMember().getAddress().toString();
                log.debug("Remote cache entry removed map event has occurred for key: " + key);
                log.debug("Remote address: " + address);
            } else {
                log.debug("Local cache entry removed map event has occurred for key: " + key);
            }
        } catch (final Exception exception) {
            log.error("Failure occurred in entryRemoved listener: " + exception.getMessage(), exception);
        }
    }

    private boolean isRemoteModification(final EntryEvent<String, TitleClientResponse> event) {
        final UUID eventInstanceId = event.getMember().getUuid();
        final UUID instanceId = hazelcastInstance.getLocalEndpoint().getUuid();
        return !instanceId.equals(eventInstanceId);
    }
}
