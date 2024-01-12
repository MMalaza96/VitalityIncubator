package za.co.discovery.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static za.co.discovery.model.request.MoviesDatabaseHeaders.X_RAPID_API_HOST;
import static za.co.discovery.model.request.MoviesDatabaseHeaders.X_RAPID_API_KEY;


public final class MovieDatabaseServiceWireMock {

    private final WireMockServer wireMockServer;

    private MovieDatabaseServiceWireMock(final WireMockConfiguration configuration) {
        this(new WireMockServer(configuration));
    }

    private MovieDatabaseServiceWireMock(final WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    public static MovieDatabaseServiceWireMock http(final int port) {
        return new MovieDatabaseServiceWireMock(WireMockConfiguration.wireMockConfig()
                .port(port)
                .needClientAuth(false));
    }

    public void start() {
        wireMockServer.start();
    }

    public void stop() {
        wireMockServer.stop();
    }

    public boolean isRunning() {
        return wireMockServer.isRunning();
    }

    public void initializeMovieDatabaseServiceRequestMatchers(final String subPath,
                                                              final LinkedHashMap<String, String> queryParams) {
        wireMockServer.stubFor(WireMock
                .get(buildUrlWithQueryParams(subPath, queryParams))
                .willReturn(aResponse()
                        .withHeader(X_RAPID_API_KEY, "testKey")
                        .withHeader(X_RAPID_API_HOST, "testHost")
                        .withBody(retrieveMovieDatabaseServiceMockResponse(queryParams))));
    }

    private String retrieveMovieDatabaseServiceMockResponse(LinkedHashMap<String, String> queryParameters) {
        String response = "";
        try {
            InputStream inputStream =
                    getClass().getClassLoader()
                            .getResourceAsStream("mockresponses/titles/"
                                    + extractFileName(queryParameters) + ".json");

            assert inputStream != null : "inputStream cannot be null";
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            response = responseStrBuilder.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return response;
    }

    private String buildUrlWithQueryParams(final String subPath, final LinkedHashMap<String, String> queryParams) {
        final StringBuilder uriBuilder = new StringBuilder(subPath);
        if (!queryParams.isEmpty()) {
            uriBuilder.append("?");
            queryParams.forEach((key, value) -> {
                if (value == null || value.isEmpty()) {
                    return;
                }
                uriBuilder.append(key).append("=").append(value).append("&");
            });
            uriBuilder.deleteCharAt(uriBuilder.length() - 1);
        }
        return uriBuilder.toString();
    }

    private String extractFileName(final LinkedHashMap<String, String> queryParameters) {
        final StringBuilder fileName = new StringBuilder();
        for (Map.Entry<String, String> queryParameter : queryParameters.entrySet()) {
            fileName.append(queryParameter.getValue());
        }
        return fileName.toString();
    }


}





