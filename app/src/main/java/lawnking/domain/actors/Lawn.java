package lawnking.domain.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lawnking.domain.direction.Direction;
import lawnking.domain.landscape.Crater;
import lawnking.domain.landscape.Surrounding;
import lawnking.domain.messages.ScanMessage;
import lawnking.domain.messages.SurroundingMessage;
import lawnking.infrastructure.Channel;
import lawnking.infrastructure.Listener;
import lawnking.infrastructure.Message;
import lawnking.domain.MowerRegistry;
import lawnking.domain.Point;
import lawnking.domain.GlobalWorldView;

public class Lawn implements Listener {
    private GlobalWorldView worldView;
    private List<MowerRegistry> mowers;
    private final List<Class<? extends Message>> SIGNALS = Arrays.asList(ScanMessage.class);
    private Channel globalChannel;

    public Lawn() {
        this.mowers = new ArrayList<>();
    }

    static class CraterLocation extends Point {

        public CraterLocation(Integer x, Integer y) {
            super(x, y);
        }
    }

    public static Lawn from(List<String> configFile) {
        // first two are length and width
        Lawn lawn = new Lawn();
        lawn.createWorldView(configFile.remove(0), configFile.remove(0));

        // register mowers
        Integer numberOfMowers = Integer.valueOf(configFile.remove(0));
        for (int i = 0; i < numberOfMowers; i++) {
            String mowerDetail = configFile.remove(0);
            String mowerDetailParts[] = mowerDetail.split(", ");
            Mower mower = new Mower(mowerDetail);
            lawn.register(mower, mowerDetailParts);
        }

        // register craters
        Integer numberOfCraters = Integer.valueOf(configFile.remove(0));
        for (int i = 0; i < numberOfCraters; i++) {
            String description = configFile.remove(0);
            String[] detail = description.split(", ");
            CraterLocation craterLocation = new CraterLocation(Integer.valueOf(detail[0]), Integer.valueOf(detail[1]));
            lawn.register(craterLocation);
        }

        // Logger.getGlobal().log(Level.INFO, ReflectionToStringBuilder.toString(lawn, ToStringStyle.MULTI_LINE_STYLE, true, true));

        return lawn;

        // I'm incessantly using collection.remove(0) to simulate list.pop, cos
        // I do not know how better to do this in Java. This is where I miss Ruby
    }

    private void register(CraterLocation craterLocation) {
        Crater crater = new Crater();
        this.worldView.register(crater, (Point) craterLocation);
    }

    private void register(Mower mower, String[] detail) {
        Point mowerLocation = new Point(Integer.valueOf(detail[0]), Integer.valueOf(detail[1]));
        this.mowers.add(new MowerRegistry(mower, mowerLocation, new Direction(detail[2])));
    }

    private void createWorldView(String length, String width) {
        this.worldView = new GlobalWorldView(Integer.valueOf(width), Integer.valueOf(length));
    }

    public void payAttention(Channel channel) {
        this.globalChannel = channel;

        SIGNALS
            .forEach(signal -> channel.subscribe(signal, this));

        mowers
            .stream()
            .map(m -> m.mower())
            .forEach(m -> m.payAttention(globalChannel));
    }

    public List<Mower> getMowers() {
        return mowers
            .stream()
            .map(mowerRegistration -> mowerRegistration.mower())
            .collect(Collectors.toList());
    }

    @Override
    public List<Class<? extends Message>> getSignals() {
        return SIGNALS;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ScanMessage) {
            reply((ScanMessage) message);
        } else {
            Logger.getGlobal().log(Level.INFO, "Received an unknown message");
        }
    }

    private void reply(ScanMessage message) {
        // if we cannot find the lawn mower asking for a scan, do nothing. No
        // harm no foul. If the agent cannot identify itself, it's a no-op.
        if (!message.getData().keySet().contains("mower_id")) {
            return;
        }

        String mowerId = message.getData().get("mower_id");
        MowerRegistry requesterMower = mowers
            .stream()
            .filter(registeredMower -> registeredMower.mower().getId() == mowerId)
            .findFirst()
            .get();

        Surrounding surrounding = this.worldView.getSurrounding(requesterMower.location());
        Map<String, String> responseData = new HashMap<>() {{
            put("mower", mowerId);
            put("surrounding", surrounding.serialize());
        }};

        globalChannel.announce(new SurroundingMessage(responseData));
    }
}
