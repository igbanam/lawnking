package lawnking.domain.messages;

import java.util.Map;

import lawnking.domain.landscape.Surrounding;
import lawnking.infrastructure.Message;

public class SurroundingMessage extends Message {
    public SurroundingMessage(Map<String, String> data) {
        super(data);
    }

    public Surrounding getSurrounding() {
        String serializedSurrounding = this.getData().get("surrounding");
        if (serializedSurrounding.isBlank()) {
            throw new IllegalStateException("Got a SurroundingMessage without a Surrounding");
        }
        return Surrounding.from(serializedSurrounding);
    }
}
