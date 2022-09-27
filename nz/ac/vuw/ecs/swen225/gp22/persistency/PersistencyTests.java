package nz.ac.vuw.ecs.swen225.gp22.persistency;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.DomainBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.dom4j.DocumentException;

public class PersistencyTests {
    @Test
    public void testLoadingLevel() {
        try {
            Domain d = Parser.loadLevel("test1.xml");
            System.out.println(d.toString());
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
            assert false : e.getMessage();
        }

    }

    @Test
    public void testSavingGame() {
        DomainBuilder db = new DomainBuilder();
        db.info(1, 2).wall(2, 2).exit(2, 3).player(3, 3);
        Domain d = db.make();

        Parser.saveLevel(d);
    }
}
