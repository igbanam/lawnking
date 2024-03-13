package lawnking.infrastructure;

import java.util.List;

public interface Listener {
    List<Class<? extends Message>> getSignals();
    void payAttention(Channel channel);
    void onMessage(Message message);
}
