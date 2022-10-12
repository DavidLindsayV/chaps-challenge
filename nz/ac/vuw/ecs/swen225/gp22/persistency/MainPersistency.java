package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.File;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;

public class MainPersistency {
    public static void main(String[] args) {

        List<Point> p = List.of(new Point(0, 0));
        Class<?> c = ActorLoader
                .getClass(new File("nz/ac/vuw/ecs/swen225/gp22/levels/Enemy.jar"),
                        "nz.ac.vuw.ecs.swen225.gp22.persistency.BasicEnemy");
        try {
            Object e = c.getDeclaredConstructor(List.class).newInstance(p);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MockPersistency.run();
    }
}
