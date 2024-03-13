package lawnking.domain.actors;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lawnking.domain.LocalWorldView;
import lawnking.domain.direction.Direction;
import lawnking.domain.landscape.Surrounding;
import lawnking.domain.messages.MowingCompleteMessage;
import lawnking.domain.messages.ScanMessage;
import lawnking.domain.messages.SurroundingMessage;
import lawnking.infrastructure.Channel;
import lawnking.infrastructure.Listener;
import lawnking.infrastructure.Message;

public class Mower implements Listener {
    private LocalWorldView worldView;
    private String id;
    private boolean isOn;
    private Surrounding currentSurrounding;
    private Channel globalChannel;
    private final List<Action> skills = Arrays.asList(new Move(), new Scan());
    private boolean lawnMowed;

    public Mower(String id) {
        this.id = id;
        this.lawnMowed = false;
        this.worldView = createWorldView();
    }

    public String getId() {
        return this.id;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public void work() {
        turnOn();

        // local OODA? — add erraticness
        // Observe
        Logger.getGlobal().log(Level.FINE, String.format("Mower[{}] begins work", id));
        while (pendingWork() && isOn()) {

            // Orient
            // no-op

            // Decide
            Action workDecision = decide();

            // Act
            workDecision.perform(this);
        }
    }

    private boolean hasCurrentSurrounding() {
        return currentSurrounding != null;
    }

    private Action decide() {
        if (hasCurrentSurrounding()) {
            return new Move();
        } else {
            return new Scan();
        }
    }

    private void turnOn() {
        isOn = true;
    }

    private boolean pendingWork() {
        return !lawnMowed;
    }

    private boolean isOn() {
        return isOn;
    }

    @Override
    public void payAttention(Channel channel) {
        this.globalChannel = channel;

        getSignals()
            .forEach(signal -> channel.subscribe(signal, this));
    }

    @Override
    public void onMessage(Message message) {
        Logger.getGlobal().info(String.format("Mower[%s] got a message: %s", id, ReflectionToStringBuilder.toString(message)));
        if (message instanceof SurroundingMessage) {
            this.worldView.update((SurroundingMessage) message);
            this.currentSurrounding = ((SurroundingMessage) message).getSurrounding();
        } else if (message instanceof MowingCompleteMessage) {
            this.lawnMowed = true;
        } else {
            Logger.getGlobal().log(Level.INFO, "Received an unknown message");
        }
    }

    @Override
    public List<Class<? extends Message>> getSignals() {
        return Arrays.asList(SurroundingMessage.class);
    }

    abstract class Action {
        public abstract void perform(Mower m);
    }

    class Mow extends Action {

        @Override
        public void perform(Mower m) {
            throw new UnsupportedOperationException("Unimplemented method 'perform'");
        }
    }

    class Move extends Action {
        // Something which the original problem posed for Mowers is that they
        // can move in two steps at once. Here, I want to break this down to
        // only moving one step at a time.

        @Override
        public void perform(Mower mower) {
            // select the destination from surrounding
            Direction intendedDirection = mower.pickDestination();
            // move in local map
            // announce the movement to global map
        }
    }

    class Scan extends Action {

        @Override
        public void perform(Mower m) {
            Logger.getGlobal().log(Level.INFO, String.format("Mower[%s] requesting scan", id));
            Map<String, String> messageData = new HashMap<>() {{
                put("mower_id", id);
            }};
            globalChannel.announce(new ScanMessage(messageData));
        }
    }

    public Direction pickDestination() {
        if (!hasCurrentSurrounding()) {
            throw new IllegalStateException("There's no surrounding to choose from.");
        }
        Random generator = new Random();
        return Direction.fromIndex(generator.nextInt(8));
    }

    private LocalWorldView createWorldView() {
        return new LocalWorldView();
    }
}
