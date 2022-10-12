package nz.ac.vuw.ecs.swen225.gp22.persistency;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.DomainBuilder;
import nz.ac.vuw.ecs.swen225.gp22.recorder.MockRecorder;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;

import org.dom4j.DocumentException;

public class PersistencyTests {
    String test1Layout = "|_|_|_|_|_|_|\n"
            + "|_|_|_|_|_|_|\n"
            + "|_|_|_|*|P|_|\n"
            + "|_|_|L|#|#|#|\n"
            + "|_|_|E|_|_|_|\n";

    String level1Layout = "|_|_|#|#|#|#|#|_|#|#|#|#|#|_|_|\n"
            + "|_|_|#|_|_|_|#|#|#|_|_|_|#|_|_|\n"
            + "|_|_|#|_|_|_|#|E|#|_|_|_|#|_|_|\n"
            + "|#|#|#|#|#|@|#|L|#|@|#|#|#|#|#|\n"
            + "|#|_|k|_|@|_|_|_|_|_|@|_|k|_|#|\n"
            + "|#|_|*|_|#|k|_|i|_|k|#|_|*|_|#|\n"
            + "|#|#|#|#|#|*|_|P|_|*|#|#|#|#|#|\n"
            + "|#|_|*|_|#|k|_|_|_|k|#|_|*|_|#|\n"
            + "|#|_|_|_|@|_|_|*|_|_|@|_|_|_|#|\n"
            + "|#|#|#|#|#|#|@|#|@|#|#|#|#|#|#|\n"
            + "|_|_|_|_|#|_|_|#|_|_|#|_|_|_|_|\n"
            + "|_|_|_|_|#|_|*|#|*|_|#|_|_|_|_|\n"
            + "|_|_|_|_|#|_|k|#|k|_|#|_|_|_|_|\n"
            + "|_|_|_|_|#|#|#|#|#|#|#|_|_|_|_|\n";

    String level2Layout = "|_|_|#|#|#|#|#|#|#|_|_|_|_|_|_|_|\n"
            + "|_|_|#|_|_|*|_|_|#|_|_|_|_|_|_|_|\n"
            + "|_|_|#|_|_|_|_|_|#|_|_|_|_|_|_|_|\n"
            + "|_|_|#|_|_|#|_|_|#|#|#|#|#|#|#|#|\n"
            + "|#|#|#|_|_|#|_|_|_|#|k|_|_|_|*|#|\n"
            + "|#|E|L|_|_|#|#|_|_|#|#|#|P|i|_|#|\n"
            + "|#|#|#|_|_|#|_|4|_|@|_|_|_|_|*|#|\n"
            + "|_|_|#|_|_|#|4|_|#|#|#|#|#|#|#|#|\n"
            + "|_|_|#|_|_|4|_|_|#|_|_|_|_|_|_|_|\n"
            + "|_|_|#|_|_|*|_|_|#|_|_|_|_|_|_|_|\n"
            + "|_|_|#|#|#|#|#|#|#|_|_|_|_|_|_|_|\n";

    @Test
    public void testLoadingLevel() {
        try {
            Domain d = Parser.loadLevel("tests/test1.xml");
            assertEquals(d.toString(), test1Layout);
        } catch (DocumentException e) {
            assert false : e.getMessage();
        }

    }

    @Test
    public void testLoadingLevel1() {
        try {
            Domain d = Parser.loadLevel("level1.xml");
            assertEquals(d.toString(), level1Layout);
        } catch (DocumentException e) {
            assert false : e.getMessage();
        }

    }

    @Test
    public void testLoadingLevel2() {
        try {
            Domain d = Parser.loadLevel("level2.xml");
            System.out.println(d.toString());
            assertEquals(d.toString(), level2Layout);
        } catch (DocumentException e) {
            assert false : e.getMessage();
        }
    }

    @Test
    public void testNoRowNumber() {
        try {
            Domain d = Parser.loadLevel("tests/noRowNumber.xml");
            assertThrows(NullPointerException.class, () -> System.out.println("No row number not detected"));
        } catch (DocumentException e) {
            assert false : e.getMessage();
        }
    }

    @Test
    public void testNoKeyColourSpecified() {
        try {
            Domain d = Parser.loadLevel("tests/keyWithNoColour.xml");
            fail("Failed to detect no colour specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testNoPlayerPosition() {
        try {
            Domain d = Parser.loadLevel("tests/noPlayer.xml");
            fail("Failed to detect no player specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (IllegalStateException e) {
            assert true;
        }
    }

    @Test
    public void testNoExitPosition() {
        try {
            Domain d = Parser.loadLevel("tests/noExit.xml");
            fail("Failed to detect no exit specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (IllegalStateException e) {
            assert true;
        }
    }

    @Test
    public void testNoDoorColourSpecified() {
        try {
            Domain d = Parser.loadLevel("tests/doorWithNoColour.xml");
            fail("Failed to detect no colour specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testNoEnemyPathSpecified() {
        try {
            Domain d = Parser.loadLevel("tests/enemyWithNoPath.xml");
            fail("Failed to detect no path specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testSavingGame() {
        runRecorder();
        DomainBuilder db = new DomainBuilder();
        db.info(1, 2).wall(2, 2).exit(2, 3).player(3, 3);

        Domain d = db.make();

        try {
            Parser.saveLevel(d);
        } catch (IOException e) {
            assert false : e.getMessage();
        }
    }

    public void runRecorder() {
        Recorder.setUp("level1.xml");
        for (int i = 0; i < 10; i++) {
            Recorder.tick(MockRecorder.randomEnum(Direction.class));
        }

    }
}
