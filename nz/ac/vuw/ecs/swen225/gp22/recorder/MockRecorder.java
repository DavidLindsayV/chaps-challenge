package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;

import org.dom4j.DocumentException;

public class MockRecorder {
    
    /* Fields */
    private Recorder recorder;

    /* 
     * Constructor 
     */
    public MockRecorder(){
        recorder = new Recorder();
    }
    
    /* Chap moved in a direction */
    public void up() { recorder.move("up"); }
    public void down() { recorder.move("down"); }
    public void left() { recorder.move("left"); }
    public void right() { recorder.move("right"); }

    /* Saving and loading a record of a game */
    public void saveGame() throws IOException { recorder.save(); }
    public void loadGame() throws IOException, DocumentException { recorder.load(); }

}
