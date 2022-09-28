package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.IOException;

import org.dom4j.DocumentException;

import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;

public class MockPersistency {
    /**
     * Runs a simulation of a game
     */
    public static void run() {

        try {
            Domain d = Parser.loadLevel("saved_game_1.xml");
            Parser.saveLevel(d);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
