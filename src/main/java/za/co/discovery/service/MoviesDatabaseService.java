package za.co.discovery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.discovery.exceptions.MoviesDatabaseServiceException;
import za.co.discovery.mapper.TitleClientResponseMapper;
import za.co.discovery.model.response.TitleClientResponse;
import za.co.discovery.model.response.TitleResponse;
import za.co.discovery.utility.HttpClientUtil;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static za.co.discovery.model.MoviesDatabaseSubPaths.TITLES;

@Service
public class MoviesDatabaseService {

    private final HttpClientUtil httpClientUtil;
    private final String baseUrl;

    private final ObjectMapper objectMapper;

    @Autowired
    public MoviesDatabaseService(final HttpClientUtil httpClientUtil,
                                 @Value("${movies-database.baseurl}")
                                 @NotNull(message = "baseUrl value cannot be null") final String baseUrl,
                                 final ObjectMapper objectMapper) {
        this.httpClientUtil = httpClientUtil;
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
    }

    public TitleResponse retrieveMovieResultById(final String id,
                                                 final Map<String, String> headers,
                                                 final Map<String, String> queryParameters) {
        final HttpRequest httpRequest = httpClientUtil.createGETHttpRequest(
                baseUrl + TITLES + id,
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
                return TitleClientResponseMapper.INSTANCE.toTitleResponse(titleClientResponse);
            } catch (final JsonProcessingException exception) {
                throw new MoviesDatabaseServiceException(
                        "Failed to parse json client response into MovieResultVO: "
                                + exception.getMessage(), exception);
            }
        } else {
            throw new MoviesDatabaseServiceException(
                    "Failure occurred when attempting to retrieve movie result by ID, " +
                            "error code " + statusCode + "was returned by client");
        }
    }

}
