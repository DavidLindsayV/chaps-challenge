package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.IOException;

import org.dom4j.DocumentException;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.recorder.MockRecorder;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

public class MockPersistency {
    /**
     * Runs a simulation of a game
     */
    public static void run() {

        try {
            Recorder.newLevel();
            for (int i = 0; i < 10; i++) {
                Recorder.tick(MockRecorder.randomEnum(Direction.class));
            }

            Domain d = Parser.loadLevel("level2.xml");
            Parser.saveLevel(d);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
