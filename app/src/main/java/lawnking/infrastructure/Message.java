package lawnking.infrastructure;

import java.util.HashMap;
import java.util.Map;

abstract public class Message {
    private Map<String, String> data;

    public Message() {
        this.data = new HashMap<>();
    }

    public Message(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }
}
