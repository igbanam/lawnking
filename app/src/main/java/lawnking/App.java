package lawnking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import lawnking.contexts.SetupContext;
import lawnking.contexts.SimulationContext;
import lawnking.domain.actors.Lawn;

public class App {
    public static void main(String[] args) {
        Lawn lawn = SetupContext.generateLawn("input.txt");

        SimulationContext.mow(lawn);
    }
}
