package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Random;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;

public class MockRecorder {

    /**
     * Get random enum clas for testing, not my method.
     * 
     * Found here: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     * 
     * @param Class<T>
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
        Recorder.newLevel();
        for(int i=0; i<10; i++){
            Recorder.tick( randomEnum(Direction.class) );
        }
        
        Recorder.save();

        Recorder.loadPartial();

        for(int i=0; i<2; i++){
            Recorder.tick( randomEnum(Direction.class) );
        }

        Recorder.save();

    }

}
