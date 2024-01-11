package za.co.discovery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.map.IMap;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.discovery.exceptions.MoviesDatabaseServiceException;
import za.co.discovery.hazelcast.cache.Cache;
import za.co.discovery.mapper.TitleClientResponseMapper;
import za.co.discovery.mapper.TitlesClientResponseMapper;
import za.co.discovery.model.persistence.Title;
import za.co.discovery.model.response.TitleClientResponse;
import za.co.discovery.model.response.TitleResponse;
import za.co.discovery.model.response.TitlesClientResponse;
import za.co.discovery.model.response.TitlesResponse;
import za.co.discovery.utility.HttpClientUtil;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static za.co.discovery.model.request.MoviesDatabaseSubPaths.TITLES;

@Slf4j
@Service
public class MoviesDatabaseService {

    private final HttpClientUtil httpClientUtil;
    private final String baseUrl;
    private final ObjectMapper objectMapper;
    private final Cache cache;

    @Autowired
    public MoviesDatabaseService(final HttpClientUtil httpClientUtil,
                                 @Value("${movies-database.baseurl}")
                                 @NotNull(message = "baseUrl value cannot be null") final String baseUrl,
                                 final ObjectMapper objectMapper,
                                 final Cache cache) {
        this.httpClientUtil = httpClientUtil;
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
        this.cache = cache;
    }

    public TitleResponse retrieveMovieResultById(final String id,
                                                 final LinkedHashMap<String, String> headers,
                                                 final LinkedHashMap<String, String> queryParameters) {
        final IMap<String, Object> titleClientResponseCacheMap = Cache
                .retrieveCacheMap(TitleClientResponse.class.getSimpleName());
        if (titleClientResponseCacheMap.containsKey(id)) {
            log.debug("Returning cached response for Movie Result with id " + id);
            final TitleClientResponse titleClientResponse = (TitleClientResponse) titleClientResponseCacheMap.get(id);
            return TitleClientResponseMapper.INSTANCE.toTitleResponse(titleClientResponse);
        }

        final HttpRequest httpRequest = httpClientUtil.createGETHttpRequest(
                baseUrl + TITLES + "/" + id,
                headers,
                queryParameters);

        final HttpResponse<String> clientResponse;
        try {
            clientResponse = httpClientUtil.executeHttpRequest(httpRequest).get();
        } catch (final InterruptedException | ExecutionException exception) {
            throw new MoviesDatabaseServiceException("Failure occurred in httpClientUtil " +
                    "when attempting to execute http request " + httpRequest, exception);
        }

        final int statusCode = clientResponse.statusCode();
        if (HttpStatus.OK.value() == statusCode) {
            final String jsonResponse = clientResponse.body();
            try {
                final TitleClientResponse titleClientResponse = objectMapper.readValue(
                        jsonResponse,
                        TitleClientResponse.class);

                cacheRetrieveMovieResultById(id, titleClientResponse);

                return TitleClientResponseMapper.INSTANCE.toTitleResponse(titleClientResponse);
            } catch (final JsonProcessingException exception) {
                throw new MoviesDatabaseServiceException(
                        "Failed to parse json client response into TitleClientResponse: "
                                + exception.getMessage(), exception);
            }
        } else {
            throw new MoviesDatabaseServiceException(
                    "Failure occurred when attempting to retrieve movie title by ID, " +
                            "error code " + statusCode + "was returned by client");
        }
    }

    public TitlesResponse retrieveMovieResults(final LinkedHashMap<String, String> headers,
                                               final LinkedHashMap<String, String> queryParameters) {
        final IMap<String, Object> titlesClientResponseCacheMap = Cache
                .retrieveCacheMap(TitlesClientResponse.class.getSimpleName());
        final String key = queryParameters.toString();
        if (titlesClientResponseCacheMap.containsKey(key)) {
            log.debug("Returning cached response for Movie Results with parameters " + key);
            final TitlesClientResponse titlesClientResponse = (TitlesClientResponse) titlesClientResponseCacheMap.get(key);
            return TitlesClientResponseMapper.INSTANCE.toTitlesResponse(titlesClientResponse);
        }

        final HttpRequest httpRequest = httpClientUtil.createGETHttpRequest(
                baseUrl + TITLES,
                headers,
                queryParameters);

        final HttpResponse<String> clientResponse;
        try {
            clientResponse = httpClientUtil.executeHttpRequest(httpRequest).get();
        } catch (final InterruptedException | ExecutionException exception) {
            throw new MoviesDatabaseServiceException("Failure occurred in httpClientUtil " +
                    "when attempting to execute http request " + httpRequest, exception);
        }

        final int statusCode = clientResponse.statusCode();
        if (HttpStatus.OK.value() == statusCode) {
            final String jsonResponse = clientResponse.body();
            try {
                final TitlesClientResponse titlesClientResponse = objectMapper.readValue(
                        jsonResponse,
                        TitlesClientResponse.class);

                cacheRetrieveMovieResults(key, titlesClientResponse);

                return TitlesClientResponseMapper.INSTANCE.toTitlesResponse(titlesClientResponse);
            } catch (final JsonProcessingException exception) {
                throw new MoviesDatabaseServiceException(
                        "Failed to parse json client response into TitlesClientResponse: "
                                + exception.getMessage(), exception);
            }
        } else {
            throw new MoviesDatabaseServiceException(
                    "Failure occurred when attempting to retrieve movie titles, " +
                            "error code " + statusCode + "was returned by client");
        }
    }

    private void cacheRetrieveMovieResults(final String queryParameters,
                                           final TitlesClientResponse titlesClientResponse) {
        Optional<List<Title>> optionalTitles = Optional.of(titlesClientResponse.getTitles());
        optionalTitles.ifPresent(titles -> {
            if (!titles.isEmpty()) {
                Cache.cache(
                        TitlesClientResponse.class.getSimpleName(),
                        queryParameters,
                        titlesClientResponse);
            }
        });
    }

    private void cacheRetrieveMovieResultById(final String id,
                                              final TitleClientResponse titleClientResponse) {
        Optional<Title> optionalTitle = Optional.of(titleClientResponse.getTitle());
        optionalTitle.ifPresent(title -> Cache.cache(
                TitleClientResponse.class.getSimpleName(),
                id,
                titleClientResponse));
    }

}
