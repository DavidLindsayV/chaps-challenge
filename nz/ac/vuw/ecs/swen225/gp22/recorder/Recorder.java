package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.swing.JFileChooser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;

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
    public static void tick(UserListener.moveType move){
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
        URL url;
        JFileChooser fileChooser = new JFileChooser("recorded_games/");
        int responce = fileChooser.showOpenDialog(null);
        if(responce == JFileChooser.APPROVE_OPTION){
            url = new File(fileChooser.getSelectedFile().getAbsolutePath()).toURI().toURL();
            
            //Here we have the document, currently it just prints the file to the console.
            Document readDocument = RecordReader.parse(url);
            System.out.println(readDocument.asXML());
        }
    }
}