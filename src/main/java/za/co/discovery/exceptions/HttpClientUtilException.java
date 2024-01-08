package za.co.discovery.exceptions;

public class HttpClientUtilException extends RuntimeException {

    private static final long serialVersionUID = -2132533981080337650L;

    public HttpClientUtilException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public HttpClientUtilException(final String message) {
        super(message);
    }

    public HttpClientUtilException(final Throwable throwable) {
        super(throwable);
    }
}
