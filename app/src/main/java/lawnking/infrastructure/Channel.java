package lawnking.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class Channel {
    private Map<Class<? extends Message>, List<Listener>> listeners = new HashMap<>();

    public void subscribe(Class<? extends Message> eventType, Listener listener) {
        listeners
            .computeIfAbsent(eventType, l -> new ArrayList<>())
            .add(listener);
    }

    public void unsubscribe(Class<? extends Message> eventType, Listener listener) {
        listeners
            .computeIfAbsent(eventType, l -> new ArrayList<>())
            .remove(listener);
    }

    public void announce(Message message) {
        Logger.getGlobal().info(String.format("New announcement -- %s", message.getClass().getSimpleName()));
        listeners
            .getOrDefault(message.getClass(), new ArrayList<>())
            .forEach(listener -> listener.onMessage(message));
    }
}
