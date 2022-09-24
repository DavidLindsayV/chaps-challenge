package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;
import java.net.MalformedURLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import nz.ac.vuw.ecs.swen225.gp22.app.moveType;

public class Recorder {

    /* Fields. */
    private static RecordWriter recWriter;
    private static Document document;

    /*
     * Refreashes the dom4j document and record writer, for a new level
     */
    public static void newLevel(){
        document = DocumentHelper.createDocument();
        recWriter = new RecordWriter(document);
    }

    /*
     * Record a given tick
     */
    public static void tick(moveType move){
        recWriter.tick(move);
    }

    /* 
     * Saves a recorded game to an xml file.
     */
    public static void save() throws IOException {
        recWriter.save();
    }

    /* 
     * Loads a game from a record xml file.
     */
    public static void load() throws MalformedURLException, DocumentException{
        RecordReader.loadDoc();
    }
}