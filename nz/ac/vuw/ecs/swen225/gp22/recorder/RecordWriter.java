package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class RecordWriter {

    /* Fields. */
    private Document document;
    private Element game;
    private Element moves;

    /*
     * Recorder constructor, writing to a given org.dom4j.Document.
     */
    public RecordWriter(Document document){
        this.document = document;
        this.game = document.addElement("game");
        this.moves = game.addElement("moves");
    }

    /*
     * Record a given move
     */
    public void move(String move){
        moves.addElement("move")
            .addAttribute("actor", "player")
            .addAttribute("tick", "0")
            .addText(move);
    }

    /* 
     * Saves the recorded game.
     */
    public void save() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmmss");  
        LocalDateTime now = LocalDateTime.now();  
        String nowStr = dtf.format(now);    

        document.addComment(nowStr );

        // Pretty print write to a xml file
        FileWriter fileWriter = new FileWriter("recorded_games/"+"Chaps Record ("+nowStr+").xml");
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(fileWriter, format);
        writer.write( document );
        writer.close();
    }

}