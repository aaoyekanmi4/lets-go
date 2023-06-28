package learn.letsgo.Domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private ResultType status = ResultType.SUCCESS;
    private ArrayList<String> messages = new ArrayList<>();
    private T payload;

    public ResultType getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void addMessage(ResultType status, String message) {
        this.status = status;
        messages.add(message);
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }
}