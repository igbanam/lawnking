package lawnking.domain.messages;

import java.util.Map;

import lawnking.infrastructure.Message;

public class MowingCompleteMessage extends Message {
    public MowingCompleteMessage(Map<String, String> data) {
        super(data);
    }
}
