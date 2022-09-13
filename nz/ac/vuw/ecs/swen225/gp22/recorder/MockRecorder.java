package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;

public class MockRecorder {

    private Recorder recorder;

    public MockRecorder(){
        recorder = new Recorder();
    }
    
    /**Move Chap in a direction */
    public void up() { recorder.move("up"); }
    public void down() { recorder.move("down"); }
    public void left() { recorder.move("left"); }
    public void right() { recorder.move("right"); }

    /**Stop moving Chap in a direction */
    public void unUp() { recorder.move("unUp"); }
    public void unDown() { recorder.move("unDown"); }
    public void unLeft() { recorder.move("unLeft"); }
    public void unRight() { recorder.move("unRight"); }

    public void exitGame() throws IOException { recorder.save(); }

}
