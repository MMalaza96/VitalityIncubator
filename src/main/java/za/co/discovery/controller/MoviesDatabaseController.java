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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.exceptions.MoviesDatabaseServiceException;
import za.co.discovery.model.response.TitleResponse;
import za.co.discovery.model.response.TitlesResponse;
import za.co.discovery.service.MoviesDatabaseService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.END_YEAR;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.GENRE;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.INFO;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.LIMIT;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.LIST;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.PAGE;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.SORT;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.START_YEAR;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.TITLE_TYPE;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.YEAR;
import static za.co.discovery.model.request.MoviesDatabaseHeaders.X_RAPID_API_HOST;
import static za.co.discovery.model.request.MoviesDatabaseHeaders.X_RAPID_API_KEY;

@Slf4j
@RestController
@RequestMapping("/movies-database")
public class MoviesDatabaseController {

    private final MoviesDatabaseService moviesDatabaseService;

    @Autowired
    public MoviesDatabaseController(final MoviesDatabaseService moviesDatabaseService) {
        this.moviesDatabaseService = moviesDatabaseService;
    }

    @GetMapping(path = "/movie-result/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleResponse> getMovieResults(@PathVariable final String id,
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

    @GetMapping(path = "/movie-result", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitlesResponse> getMovieResults(@RequestHeader(X_RAPID_API_KEY) final String x_rapid_api_key,
                                                          @RequestHeader(X_RAPID_API_HOST) final String x_rapid_api_host,
                                                          @RequestParam(GENRE) final String genre,
                                                          @RequestParam(START_YEAR) final String startYear,
                                                          @RequestParam(TITLE_TYPE) final String titleType,
                                                          @RequestParam(LIST) final String list,
                                                          @RequestParam(YEAR) final String year,
                                                          @RequestParam(SORT) final String sort,
                                                          @RequestParam(PAGE) final String page,
                                                          @RequestParam(INFO) final String info,
                                                          @RequestParam(END_YEAR) final String endYear,
                                                          @RequestParam(LIMIT) final String limit) {
        TitlesResponse titlesResponse = new TitlesResponse();
        try {
            titlesResponse = moviesDatabaseService.retrieveMovieResults(
                    extractMap(
                            entry(X_RAPID_API_KEY, x_rapid_api_key),
                            entry(X_RAPID_API_HOST, x_rapid_api_host)),
                    extractMap(
                            entry(GENRE, genre),
                            entry(START_YEAR, startYear),
                            entry(TITLE_TYPE, titleType),
                            entry(LIST, list),
                            entry(YEAR, year),
                            entry(SORT, sort),
                            entry(PAGE, page),
                            entry(INFO, info),
                            entry(END_YEAR, endYear),
                            entry(LIMIT, limit)));

            titlesResponse.setMessage("Successfully retrieved MovieResults");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(titlesResponse);

        } catch (final MoviesDatabaseServiceException exception) {
            log.error("Failure occurred: " + exception.getMessage(), exception);
            titlesResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            titlesResponse.setMessage("Operation Failed, please contact system administrator");
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(titlesResponse);
        }
    }

    @SafeVarargs
    private Map<String, String> extractMap(final Map.Entry<String, String>... entries) {
        final Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            headers.put(entry.getKey(), entry.getValue());
        }
        return headers;
    }

}
