package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;

/** 
 * The Recorder class, the place where every other class will access Recorder/Replay functions/methods. 
 * 
 * @author Kalani Sheridan - ID: 300527652   
 */
public class Recorder {

    /** 
     * Fields. 
     */
    private static RecordWriter recWriter;
    private static Document document;
    private static final Class<Direction> clazz = Direction.class;
    private static String level;

    /**
     * Called when a new level is created/started.
     * 
     * Creates the dom4j document and record writer, for a new level
     * 
     * @param level - The level xml name
     */
    public static void setUp(String level){
        System.out.println("RECORDER: Recorder has been setup.");
        document = DocumentHelper.createDocument();
        recWriter = new RecordWriter(document, level);
    }

    /**
     * Record a given tick.
     * 
     * @param <E> - Generic enum class
     * @param move - The move enum
     */
    public static <E extends Enum<E>> void tick(E move){
        recWriter.tick(move);
    }


    /**
     * Saves a recorded game to an xml file.
     * 
     * @param directory - The directory of where to save the game
     */
    public static void save(String directory) {
        System.out.println("RECORDER: Recorder has saved.");
        try {
            recWriter.save(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Loads a level which is partially completed.
     */
    public static void loadPartial() {
        try {
            RecordReader.loadPartial(clazz);
        } catch (MalformedURLException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Loads a game from a record xml file.
     */
    public static List<Direction> load() {
        try {
            return RecordReader.loadDoc(clazz);
        } catch (MalformedURLException | DocumentException e) {
            System.out.println("Load failed: "+e);
            return List.of();
        }
    }

    /**
     * Sets a new record writer. 
     *
     * @param writer - The new writer to set.
     */
    public static void setWriter(RecordWriter writer){
        recWriter = writer;
    }


    /**
     * Sets a new Document.
     * 
     * @param doc - The new document to set
     */
    public static void setDocument(Document doc){
        document = doc;
    }


    /**
     * Gets the level.
     */
    public static String getLevel(){ 
        if(level == null) return "level1.xml";
        return Recorder.level; 
    }


    /**
     * Sets the level.
     * 
     * @param levelToSet
     */
    public static void setLevel(String levelToSet){ 
        Recorder.level = levelToSet; 
    }
}