package nz.ac.vuw.ecs.swen225.gp22.persistency;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.recorder.MockRecorder;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.dom4j.DocumentException;

/**
 * Class to store all tests for the persistency module.
 *
 * Student ID: 3005 30113
 *
 * @author GeorgiaBarrand
 *
 */
public class PersistencyTests {
    String test1Layout = "|_|_|_|_|_|_|\n" + "|_|_|_|_|_|_|\n" + "|_|_|_|*|P|_|\n" + "|_|_|L|#|#|#|\n"
            + "|_|_|E|_|_|_|\n";

    String level1Layout = "|_|_|#|#|#|#|#|_|#|#|#|#|#|_|_|\n" + "|_|_|#|_|_|*|#|#|#|*|_|_|#|_|_|\n"
            + "|_|_|#|_|_|_|#|E|#|_|_|_|#|_|_|\n" + "|#|#|#|#|#|@|#|L|#|@|#|#|#|#|#|\n"
            + "|#|_|k|_|@|_|_|_|_|_|@|_|k|_|#|\n" + "|#|_|*|_|#|k|_|i|_|k|#|_|*|_|#|\n"
            + "|#|#|#|#|#|*|_|P|_|*|#|#|#|#|#|\n" + "|#|_|*|_|#|k|_|_|_|k|#|_|*|_|#|\n"
            + "|#|_|_|_|@|_|_|*|_|_|@|_|_|_|#|\n" + "|#|#|#|#|#|#|@|#|@|#|#|#|#|#|#|\n"
            + "|_|_|_|_|#|_|_|#|_|_|#|_|_|_|_|\n" + "|_|_|_|_|#|_|*|#|*|_|#|_|_|_|_|\n"
            + "|_|_|_|_|#|_|k|#|k|_|#|_|_|_|_|\n" + "|_|_|_|_|#|#|#|#|#|#|#|_|_|_|_|\n";

    String level2Layout = "|_|_|#|#|#|#|#|#|#|_|_|_|_|_|_|_|\n" + "|_|_|#|_|_|*|_|_|#|_|_|_|_|_|_|_|\n"
            + "|_|_|#|_|_|_|_|_|#|_|_|_|_|_|_|_|\n" + "|_|_|#|_|_|#|_|_|#|#|#|#|#|#|#|#|\n"
            + "|#|#|#|_|_|#|_|_|_|#|k|_|_|_|*|#|\n" + "|#|E|L|_|_|#|#|_|_|#|#|#|P|i|_|#|\n"
            + "|#|#|#|_|_|#|_|4|_|@|_|_|_|_|*|#|\n" + "|_|_|#|_|_|#|4|_|#|#|#|#|#|#|#|#|\n"
            + "|_|_|#|_|_|4|_|_|#|_|_|_|_|_|_|_|\n" + "|_|_|#|_|_|*|_|_|#|_|_|_|_|_|_|_|\n"
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
            assertEquals(d.toString(), level2Layout);
        } catch (DocumentException e) {
            assert false : e.getMessage();
        }
    }

    @Test
    public void testNoRowNumber() {
        try {
            Parser.loadLevel("tests/noRowNumber.xml");
            fail("Failed to detect no row number specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testNoColNumber() {
        try {
            Parser.loadLevel("tests/noColNumber.xml");
            fail("Failed to detect no col number specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testNoLevelNumber() {
        try {
            Parser.loadLevel("tests/noLevelNumber.xml");
            fail("Failed to detect no level number specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testNoKeyColourSpecified() {
        try {
            Parser.loadLevel("tests/keyWithNoColour.xml");
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
            Parser.loadLevel("tests/noPlayer.xml");
            fail("Failed to detect no player specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (IllegalArgumentException e) {
            assert true;
        } catch (IllegalStateException e) {
            assert true;
        }
    }

    @Test
    public void testNoExitPosition() {
        try {
            Parser.loadLevel("tests/noExit.xml");
            fail("Failed to detect no exit specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (IllegalArgumentException e) {
            assert true;
        } catch (IllegalStateException e) {
            assert true;
        }
    }

    @Test
    public void testNoDoorColourSpecified() {
        try {
            Parser.loadLevel("tests/doorWithNoColour.xml");
            fail("Failed to detect no colour specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testEnemyWithNoCol() {
        try {
            Parser.loadLevel("tests/enemyWithNoCol.xml");
            fail("Failed to detect no col specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testNoEnemyPathSpecified() {
        try {
            Parser.loadLevel("tests/enemyWithNoPath.xml");
            fail("Failed to detect no path specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testBadPathSpecified() {
        try {
            Parser.loadLevel("tests/enemyWithBadPath.xml");
            fail("Failed to detect bad path specified");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testColourNodeWithNoCol() {
        try {
            Parser.loadLevel("tests/keyWithNoCol.xml");
            fail("Failed to detect no col");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testIncorrectFilename() {
        try {
            Parser.loadLevel("blah");
            fail("Failed to detect bad filename");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test
    public void testNullFilename() {
        try {
            Parser.loadLevel(null);
            fail("Failed to detect null filename");
        } catch (DocumentException e) {
            assert false : e.getMessage();
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test
    public void testLoadingSavedGame() {
        try {
            File directory = new File("nz/ac/vuw/ecs/swen225/gp22/levels/saved_games");
            int initialFileCount = directory.list().length;
            MockPersistency.run("saved_games/test_saved_game.xml");
            int newFileCount = directory.list().length;
            assert newFileCount == initialFileCount + 1 : "A new file was not created";
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testLoadingAndSavingLevel2() {
        try {
            File directory = new File("nz/ac/vuw/ecs/swen225/gp22/levels/saved_games");
            int initialFileCount = directory.list().length;
            MockPersistency.run("level2.xml");
            int newFileCount = directory.list().length;
            assert newFileCount != initialFileCount + 1 : "A new file was not created";
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    /**
     * Method to run the mock recorder so that the parser can successfully save.
     */
    public void runRecorder() {
        Recorder.setUp("level1.xml");
        for (int i = 0; i < 10; i++) {
            Recorder.tick(MockRecorder.randomEnum(Direction.class));
        }
    }
}
