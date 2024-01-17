package za.co.discovery.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import za.co.discovery.model.response.TitlesResponse;
import za.co.discovery.utility.CurlConverter;
import za.co.discovery.wiremock.MovieDatabaseServiceWireMock;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static java.util.Map.entry;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.GENRE;
import static za.co.discovery.model.request.MovieDatabaseQueryParameters.YEAR;
import static za.co.discovery.model.request.MoviesDatabaseSubPaths.TITLES;

@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieDatabaseControllerTest {

    private static final Integer PORT = 8372;
    private static final MovieDatabaseServiceWireMock movieDatabaseServiceWireMock = MovieDatabaseServiceWireMock
            .http(PORT);

    @BeforeAll
    public static void setup() {
        movieDatabaseServiceWireMock.initializeMovieDatabaseServiceRequestMatchers(
                TITLES,
                extractMap(entry(GENRE, "Drama"), entry(YEAR, "2022")));
        movieDatabaseServiceWireMock.start();
        if (!movieDatabaseServiceWireMock.isRunning()) {
            throw new IllegalStateException("Mock movie database service failed to start");
        }
    }

    @Test
    @DisplayName("WhenValidRequestThenSuccessResponse")
    public void WhenValidRequestThenSuccessResponse() throws IOException {
        final String curlCommand = readFileFromResources("postman/TitlesSuccessResponse.txt");

        final ResponseEntity<TitlesResponse> responseEntity = CurlConverter.executeCurlCommand(
                curlCommand, HttpMethod.GET, TitlesResponse.class);

        Assertions.assertNotNull(responseEntity, "responseEntity is null");
        Assertions.assertEquals(responseEntity.getStatusCode().value(), 200,
                "Success status code expected");
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getTitles().size() > 0,
                "List of titles cannot be null");
    }

    @Test
    @DisplayName("WhenRepeatValidRequestThenCacheSuccessResponse")
    public void WhenRepeatValidRequestThenCacheSuccessResponse() throws IOException {
        final String curlCommand = readFileFromResources("postman/TitlesSuccessResponse.txt");

        final Long startTime = System.currentTimeMillis();
        final ResponseEntity<TitlesResponse> responseEntity = CurlConverter.executeCurlCommand(
                curlCommand, HttpMethod.GET, TitlesResponse.class);
        final Long endTime = System.currentTimeMillis();

        Assertions.assertNotNull(responseEntity, "responseEntity is null");
        Assertions.assertEquals(responseEntity.getStatusCode().value(), 200,
                "Success status code expected");
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getTitles().size() > 0,
                "List of titles cannot be null");

        final Long repeatStartTime = System.currentTimeMillis();
        final ResponseEntity<TitlesResponse> cachedResponseEntity = CurlConverter.executeCurlCommand(
                curlCommand, HttpMethod.GET, TitlesResponse.class);
        final Long repeatEndTime = System.currentTimeMillis();

        Assertions.assertNotNull(cachedResponseEntity, "responseEntity is null");
        Assertions.assertEquals(cachedResponseEntity.getStatusCode().value(), 200,
                "Success status code expected");
        Assertions.assertTrue(Objects.requireNonNull(cachedResponseEntity.getBody()).getTitles().size() > 0,
                "List of titles cannot be null");
        Assertions.assertEquals(responseEntity.getBody(), cachedResponseEntity.getBody(),
                "Initial response and cached response should be equal");
        Assertions.assertTrue((endTime - startTime) > (repeatEndTime - repeatStartTime),
                "Repetition of same request should result in faster cached response");
    }

    @Test
    @DisplayName("WhenInValidRequestThenBadRequestResponse")
    public void WhenInValidRequestThenBadRequestResponse() throws IOException {
        final String curlCommand = readFileFromResources("postman/TitlesBadRequestFailureResponse.txt");
        final Throwable throwable = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> CurlConverter.executeCurlCommand(curlCommand, HttpMethod.GET, TitlesResponse.class));

        Assertions.assertTrue(throwable.getMessage().contains("400"),
                "expected bad request(400) response");
    }

    @AfterAll
    public static void exit() {
        movieDatabaseServiceWireMock.stop();
        if (movieDatabaseServiceWireMock.isRunning()) {
            throw new IllegalStateException("Failed to stop Mock movie database service");
        }
    }

    private static String readFileFromResources(final String filePath) throws IOException {
        final ClassLoader classLoader = MovieDatabaseControllerTest.class.getClassLoader();

        try (final InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + filePath);
            }

            try (final Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)
                    .useDelimiter("\\A")) {
                return scanner.hasNext() ? scanner.next() : "";
            }
        }
    }

    @SafeVarargs
    private static LinkedHashMap<String, String> extractMap(final Map.Entry<String, String>... entries) {
        final LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            headers.put(entry.getKey(), entry.getValue());
        }
        return headers;
    }

}
