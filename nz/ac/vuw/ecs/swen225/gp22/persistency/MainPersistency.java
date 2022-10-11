package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.File;

public class MainPersistency {
    public static void main(String[] args) {

        ActorLoader.getClass(new File("lib/Enemy.jar"));
        MockPersistency.run();
    }
}
