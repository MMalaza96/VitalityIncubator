package za.co.discovery.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import za.co.discovery.model.enums.DataSerializerEnum;
import za.co.discovery.model.response.TitleClientResponse;
import za.co.discovery.model.response.TitlesClientResponse;

import java.util.List;

@Configuration
public class HazelcastConfiguration {

    public static final List<Class<?>> CACHED_ITEMS = List.of(
            TitlesClientResponse.class,
            TitleClientResponse.class);

    @Value("${hazelcast.map.entry.ttl:360}")
    private Integer ttl; //seconds

    @Value("${hazelcast.map.entry.max-idle:400}")
    private Integer maxIdle; //seconds

    @Bean("hazelcastInstance")
    @Scope("singleton")
    public HazelcastInstance getHazelcastInstance() {
        return Hazelcast.newHazelcastInstance(createConfig());
    }

    private Config createConfig() {
        final Config config = new Config();
        attachConfig(config);
        attachDataSerializers(config);
        return config;
    }

    //TODO: make properties configurable per item through prefix?
    private void attachConfig(final Config config) {
        CACHED_ITEMS.forEach(item -> {
            final MapConfig mapConfig = new MapConfig(item.getSimpleName());
            mapConfig.setTimeToLiveSeconds(ttl);
            mapConfig.setMaxIdleSeconds(maxIdle);
            config.addMapConfig(mapConfig);
        });
    }

    private void attachDataSerializers(final Config config) {
        final SerializationConfig serializationConfig = config.getSerializationConfig();
        final List<DataSerializerEnum> dataSerializerEnums = List.of(DataSerializerEnum.values());
        dataSerializerEnums.forEach(dataSerializerEnum -> {
            final SerializerConfig serializerConfig = new SerializerConfig()
                    .setImplementation(dataSerializerEnum.getSerializer())
                    .setTypeClass(dataSerializerEnum.getClazz());
            serializationConfig.addSerializerConfig(serializerConfig);
        });
        config.setSerializationConfig(serializationConfig);
    }
}
