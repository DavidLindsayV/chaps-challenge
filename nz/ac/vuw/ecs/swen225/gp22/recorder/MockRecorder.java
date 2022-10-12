package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Random;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;

public class MockRecorder {

    /**
     * Get random enum clas for testing, not my method.
     * Found here: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     * 
     * @param <T>  - Generic enum type
     * @param clazz  - The enum class
     * @return
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Runs a simulation of a game
     */
    public static void run() {
        Recorder.setUp("level1.xml");
        for(int i=0; i<1000; i++){
            Recorder.tick( randomEnum(Direction.class) );
        }
        
        Recorder.save("recorded_games/");
    }

}
