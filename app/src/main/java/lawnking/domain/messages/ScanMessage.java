package lawnking.domain.messages;

import java.util.Map;

import lawnking.infrastructure.Message;

public class ScanMessage extends Message {
    public ScanMessage(Map<String, String> data) {
        super(data);
    }
}
