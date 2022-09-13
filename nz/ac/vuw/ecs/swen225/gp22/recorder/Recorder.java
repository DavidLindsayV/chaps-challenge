package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Recorder {

    /** 
     * Creates the document of the recorded game.
     */
    private static Document createDocument(String nowStr) {
        Document document = DocumentHelper.createDocument();
        Element TimeDate = document.addElement("TimeDate")
            .addText(nowStr);
        return document;
    }

    /** 
     * Saves the recorded game.
     */
    public static void save() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmmss");  
        LocalDateTime now = LocalDateTime.now();  
        String nowStr = dtf.format(now);
        Document document = Recorder.createDocument(nowStr);
        FileWriter out = new FileWriter("recorded_games/"+"Chaps Record ("+nowStr+").xml");
        document.write(out);
        out.close();
    }

}
