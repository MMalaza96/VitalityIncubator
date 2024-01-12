package za.co.discovery.utility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import za.co.discovery.exceptions.HttpClientUtilException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
public class HttpClientUtil {
    private final String scheme;
    private final Integer timeout;

    public HttpClientUtil(@Value("${api.timeout}")
                          @NotNull(message = "timeout value cannot be null") Integer timeout,
                          @Value("${api.scheme}")
                          @NotBlank(message = "scheme value cannot be null") String scheme) {
        this.timeout = timeout;
        this.scheme = scheme + "://";
    }

    public HttpRequest createGETHttpRequest(final String url,
                                            final LinkedHashMap<String, String> headers,
                                            final LinkedHashMap<String, String> queryParameters) {
        try {
            return HttpRequest.newBuilder()
                    .uri(buildUriWithQueryParams(url, queryParameters))
                    .version(HttpClient.Version.HTTP_2) //TODO: tie to a property
                    .timeout(Duration.of(timeout, SECONDS))
                    .headers(buildHeadersArray(headers))
                    .GET()
                    .build();
        } catch (final Exception exception) {
            throw new HttpClientUtilException("Failure occurred when attempting to create GET http request: "
                    + exception.getMessage(), exception);
        }
    }

    @Async
    public CompletableFuture<HttpResponse<String>> executeHttpRequest(final HttpRequest request) {
        try {
            return HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (final Exception exception) {
            throw new HttpClientUtilException("Failure occurred when attempting to send async http request: "
                    + exception.getMessage(), exception);
        }
    }

    private URI buildUriWithQueryParams(final String baseUrl, final LinkedHashMap<String, String> queryParams) {
        final StringBuilder uriBuilder = new StringBuilder(scheme + baseUrl);
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

        try {
            return new URI(uriBuilder.toString());
        } catch (final URISyntaxException exception) {
            throw new HttpClientUtilException("Failure occurred when building URI: "
                    + exception.getMessage(), exception);
        }
    }

    private String[] buildHeadersArray(final LinkedHashMap<String, String> headers) {
        final List<String> headersArray = new ArrayList<>();
        headers.forEach((key, value) -> {
            headersArray.add(key);
            headersArray.add(value);
        });
        return headersArray.toArray(new String[0]);
    }
}
