package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Random;

import javax.swing.JFileChooser;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
* The class to create mock tick input so we could create test 
* the code we wrote, such as saving and loading.
*
* @author Kalani Sheridan - ID: 300527652
*/
public class MockRecorder {
    private static final Random RAND = new Random();
    /**
     * Get random enum clas for testing, not my method.
     * Found here: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     * 
     * @param <T>  - Generic enum type
     * @param clazz  - The enum class
     * @return - The enum we have got
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = RAND.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Runs a mock simulation of a game.
     */
    public static void run() {
        
        // //Testing saving a game
        // Recorder.setUp("level1.xml");
        // for(int i=0; i<10; i++){
        //     Recorder.tick( randomEnum(Direction.class) );
        // }
        // Recorder.save("nz/ac/vuw/ecs/swen225/gp22/levels/completed_records/");

        //Testing loading a partially complete game
        Recorder.setUp("level1.xml");
        URL url;
        JFileChooser fileChooser = new JFileChooser(
            new File(System.getProperty("user.dir")).getAbsolutePath() +
            "/nz/ac/vuw/ecs/swen225/gp22/levels/"
        );
        int responce = fileChooser.showOpenDialog(null);
        if(responce == JFileChooser.APPROVE_OPTION){
            try {
                url = new File(fileChooser.getSelectedFile().getAbsolutePath()).toURI().toURL();
                Recorder.loadPartial(  url.toString() );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
           
        }
        for(int i=0; i<5; i++){
            Recorder.tick( randomEnum(Direction.class) );
        }
        Recorder.save("nz/ac/vuw/ecs/swen225/gp22/levels/completed_records/");
    }
}
