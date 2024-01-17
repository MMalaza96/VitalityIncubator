package za.co.discovery.utility;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurlConverter {

    public static <T> ResponseEntity<T> executeCurlCommand(final String curlCommand,
                                                           final HttpMethod method,
                                                           final Class<T> responseType) {
        final String url = extractUrl(curlCommand);
        final HttpHeaders headers = extractHeaders(curlCommand);

        final RestTemplate restTemplate = new RestTemplate();

        final HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    private static String extractUrl(final String curlCommand) {
        final Matcher matcher = Pattern.compile("curl --location '([^']*)'").matcher(curlCommand);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static HttpHeaders extractHeaders(final String curlCommand) {
        final HttpHeaders headers = new HttpHeaders();

        final Matcher matcher = Pattern.compile("--header '(.*?)'").matcher(curlCommand);
        while (matcher.find()) {
            final String header = matcher.group(1);
            final String[] parts = header.split(": ");
            if (parts.length == 2) {
                headers.add(parts[0], parts[1]);
            }
        }

        return headers;
    }
}
