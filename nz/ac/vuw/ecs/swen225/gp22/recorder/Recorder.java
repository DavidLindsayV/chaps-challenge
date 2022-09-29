package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;
import java.net.MalformedURLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;

public class Recorder {

    /** 
     * Fields. 
     */
    private static RecordWriter recWriter;
    private static Document document;
    private static final Class<Direction> clazz = Direction.class;

    /**
     * Called when a new level is created.
     * 
     * Creates the dom4j document and record writer, for a new level
     */
    public static void setUp(){
        document = DocumentHelper.createDocument();
        recWriter = new RecordWriter(document);
    }

    /**
     * Record a given tick
     */
    public static <E extends Enum<E>> void tick(E move){
        recWriter.tick(move);
    }

    /**
     * Saves a recorded game to an xml file.
     */
    public static void save() {
        try {
            recWriter.save();
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** 
     * Loads a game from a record xml file.
     */
    public static void load() {
        try {
            RecordReader.loadDoc(clazz);
        } catch (MalformedURLException | DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** 
     * Sets a new record writer.
     */
    public static void setWriter(RecordWriter writer){
        recWriter = writer;
    }
    
    /** 
     * Sets a new Document.
     */
    public static void setDocument(Document doc){
        document = doc;
    }

}