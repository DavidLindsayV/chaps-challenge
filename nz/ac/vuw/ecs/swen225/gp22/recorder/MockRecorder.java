package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.dom4j.DocumentException;




public class MockRecorder {

    /**
     * Runs a simulation of a game
     */
    public static void run() {
        final String[] moveArr = {"up","down", "left", "right"};
        int min = 0;
        int max = 3;

        Recorder.newGame();

        Recorder.tick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        Recorder.tick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        Recorder.tick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        Recorder.tick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        Recorder.tick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        Recorder.tick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});

        Recorder.tick(new HashMap<String,String>(){{ 
            put("player",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant1",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
            put("ant2",moveArr[ThreadLocalRandom.current().nextInt(min, max+1)]);
        }});
        
        try {
            Recorder.save();
            Recorder.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
