package za.co.discovery.exceptions;

public class MoviesDatabaseServiceException extends RuntimeException {

    private static final long serialVersionUID = 3898652953743367118L;

    public MoviesDatabaseServiceException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public MoviesDatabaseServiceException(final String message) {
        super(message);
    }

    public MoviesDatabaseServiceException(final Throwable throwable) {
        super(throwable);
    }
}
