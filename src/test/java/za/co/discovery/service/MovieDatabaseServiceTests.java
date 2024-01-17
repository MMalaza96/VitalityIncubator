package za.co.discovery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import za.co.discovery.exceptions.MoviesDatabaseServiceException;
import za.co.discovery.hazelcast.cache.Cache;
import za.co.discovery.model.response.TitleClientResponse;
import za.co.discovery.utility.HttpClientUtil;

import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
public class MovieDatabaseServiceTests {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HazelcastInstance hazelcastInstance;

    @Mock
    private Cache cache;

    @Mock
    private HttpClientUtil httpClientUtil;

    @InjectMocks
    private MoviesDatabaseService moviesDatabaseService;
    
    @Test
    public void WhenHttpClientUtilFailureThenThrowMovieDatabaseServiceException() throws ExecutionException, InterruptedException {
        mockStatic(Cache.class);
        final IMap iMap = Mockito.mock(IMap.class);
        when(Cache.retrieveCacheMap(TitleClientResponse.class.getSimpleName())).thenReturn(iMap);

        final CompletableFuture response = Mockito.mock(CompletableFuture.class);
        when(response.get()).thenThrow(ExecutionException.class);
        when(httpClientUtil.executeHttpRequest(any())).thenReturn(response);

        Assertions.assertThrows(MoviesDatabaseServiceException.class,
                () -> moviesDatabaseService.retrieveMovieResultById(
                        "44225GJ#",
                        new LinkedHashMap<>(),
                        new LinkedHashMap<>()));
    }
}
