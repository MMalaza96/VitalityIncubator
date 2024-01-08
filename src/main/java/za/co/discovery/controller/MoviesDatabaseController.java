package za.co.discovery.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import java.util.LinkedHashMap;
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
                    new LinkedHashMap<>());
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
                                                          @RequestParam(value = GENRE, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String genre,
                                                          @RequestParam(value = START_YEAR, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String startYear,
                                                          @RequestParam(value = TITLE_TYPE, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String titleType,
                                                          @RequestParam(value = LIST, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String list,
                                                          @RequestParam(value = YEAR, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String year,
                                                          @RequestParam(value = SORT, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String sort,
                                                          @RequestParam(value = PAGE, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String page,
                                                          @RequestParam(value = INFO, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String info,
                                                          @RequestParam(value = END_YEAR, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String endYear,
                                                          @RequestParam(value = LIMIT, required = false, defaultValue = StringUtils.EMPTY)
                                                              final String limit) {
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
    private LinkedHashMap<String, String> extractMap(final Map.Entry<String, String>... entries) {
        final LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            headers.put(entry.getKey(), entry.getValue());
        }
        return headers;
    }

}
