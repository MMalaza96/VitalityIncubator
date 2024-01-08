package za.co.discovery.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private String message;
    private int statusCode;
    private final long timestamp;

    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }
}