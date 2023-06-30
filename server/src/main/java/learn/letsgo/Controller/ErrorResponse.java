package learn.letsgo.Controller;

import learn.letsgo.Domain.Result;
import learn.letsgo.Domain.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final List<String> message;


    private final HttpStatus status;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<String> getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorResponse(String message, HttpStatus status) {
        this.message = List.of(message);
        this.status = status;
    }

    public static <T> ResponseEntity<Object> build(Result<T> result) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (result.getStatus() == null || result.getStatus() == ResultType.INVALID) {
            status = HttpStatus.BAD_REQUEST;
        } else if (result.getStatus() == ResultType.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(result.getMessages(), status);
    }
}

