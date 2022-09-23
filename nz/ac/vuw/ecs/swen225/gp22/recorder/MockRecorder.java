package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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

    /* 
     * Sends a tick to the recorder. 
     */
    public void sendTick(Map<String,String> moveMap){
        this.recorder.tick(moveMap);
    }

    /* Saving and loading a record of a game */
    public void saveGame() throws IOException { recorder.save(); }
    public void loadGame() throws IOException, DocumentException { recorder.load(); }


    /**
     * Runs a simulation of a game
     */
    public static void run() {
        final String[] moveArr = {"up","down", "left", "right"};
        int min = 0;
        int max = 3;

        MockRecorder mock = new MockRecorder();
        mock.sendTick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        mock.sendTick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        mock.sendTick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        mock.sendTick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        mock.sendTick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        mock.sendTick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        mock.sendTick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});
        
        try {
            mock.saveGame();
            mock.loadGame();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
