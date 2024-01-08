package za.co.discovery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.exceptions.MoviesDatabaseServiceException;
import za.co.discovery.model.response.TitleResponse;
import za.co.discovery.service.MoviesDatabaseService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static za.co.discovery.model.MoviesDatabaseHeaders.X_RAPID_API_HOST;
import static za.co.discovery.model.MoviesDatabaseHeaders.X_RAPID_API_KEY;

@Slf4j
@RestController
@RequestMapping("/movies-database")
public class MoviesDatabaseController {

    private final MoviesDatabaseService moviesDatabaseService;

    @Autowired
    public MoviesDatabaseController(final MoviesDatabaseService moviesDatabaseService) {
        this.moviesDatabaseService = moviesDatabaseService;
    }

    //TODO: add list version of this endpoint and integrate filtering parameters
    @GetMapping(path = "/movie-result/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleResponse> getMovieResult(@PathVariable final String id,
                                                        @RequestHeader(X_RAPID_API_KEY) final String x_rapid_api_key,
                                                        @RequestHeader(X_RAPID_API_HOST) final String x_rapid_api_host) {
        TitleResponse titleResponse = new TitleResponse();
        try {
            titleResponse = moviesDatabaseService.retrieveMovieResultById(id,
                    extractMap(entry(X_RAPID_API_KEY, x_rapid_api_key), entry(X_RAPID_API_HOST, x_rapid_api_host)),
                    new HashMap<>());
            titleResponse.setMessage("Successfully retrieved MovieResult with id " + id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(titleResponse);

        } catch (final MoviesDatabaseServiceException exception) {
            log.error("Failure occurred: " + exception.getMessage(), exception);
            titleResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            titleResponse.setMessage("Operation Failed, please contact system administrator");
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(titleResponse);
        }
    }

    private Map<String, String> extractMap(final Map.Entry<String, String>... entries) {
        final Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            headers.put(entry.getKey(), entry.getValue());
        }
        return headers;
    }

}
