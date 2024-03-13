package lawnking.contexts;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lawnking.domain.actors.Lawn;
import lawnking.domain.actors.Mower;
import lawnking.infrastructure.Channel;

public class SimulationContext {

    public static void mow(Lawn lawn) {
        Channel communicationChannel = new Channel();
        List<Mower> mowers = lawn.getMowers();

        // Actors
        lawn.payAttention(communicationChannel);

        // Decisions:
        //   (1) OODA — because if not why not?
        //   (2) FCFS — cos this is a proof of concept.
        //   (3) No parallelism — because no performance constraint

        Logger.getGlobal().log(Level.INFO, "Simulation Begin");

        mowers.forEach(m -> m.work());

        Logger.getGlobal().log(Level.INFO, "Simulation Complete");
    }
}
