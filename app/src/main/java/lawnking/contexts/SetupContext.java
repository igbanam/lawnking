package lawnking.contexts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import lawnking.App;
import lawnking.domain.actors.Lawn;

public class SetupContext {
    public static Lawn generateLawn(String filename) {
        List<String> configFile = new ArrayList<>();

        Path inputPath = Path.of(App.class.getClassLoader().getResource("input.txt").getPath());
        try {
            configFile = Files.readAllLines(inputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (configFile.isEmpty()) {
            Logger.getGlobal().info("No config file found.");
            return null;
        }

        return Lawn.from(configFile);
    }
}
