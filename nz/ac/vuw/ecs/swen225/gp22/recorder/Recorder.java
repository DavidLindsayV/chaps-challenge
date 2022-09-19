package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFileChooser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class Recorder {

    /* Fields. */
    private RecordWriter recWriter;
    private Document document;

    /*
     * Recorder constructor
     */
    public Recorder(){
        this.document = DocumentHelper.createDocument();
        this.recWriter = new RecordWriter(this.document);
    }

    /*
     * Recorder record a given move
     */
    public void move(String move){
        this.recWriter.move(move);
    }

    /* 
     * Saves a recorded game to an xml file.
     */
    public void save() throws IOException {
        this.recWriter.save();
    }

    /* 
     * Loads a game from a record xml file.
     */
    public void load() throws MalformedURLException, DocumentException{
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
