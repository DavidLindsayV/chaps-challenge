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
    private int tickNum;

    /**
     * Recorder constructor, writing to a given org.dom4j.Document.
     */
    public RecordWriter(Document document){
        this.document = document;
        this.game = this.document.addElement("game");
        this.tickNum = 0;
    }

    /**
     * Records a tick and what happens within the tick.
     * 
     * The move map is <Actor name/id, move>, we can change this later from move to action, if we need to add more actions.
     * 
     */
    public <E extends Enum<E>> void tick(E move ){
        Element tick = this.game.addElement("tick")
            .addAttribute("tick", this.tickNum+"");
        
        this.move(tick, "player", move.name());
        tickNum++;
    }

    /**
     * Record a given move
     */
    private void move(Element tick, String actor,String move){
        tick.addElement("move")
            .addAttribute("actor", actor)
            .addText(move);
    }

    /** 
     * Saves the recorded game.
     */
    public void save(String dir) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmmss");  
        LocalDateTime now = LocalDateTime.now();  
        String nowStr = dtf.format(now);    

        document.addComment(nowStr);

        // Pretty print write to a xml file
        FileWriter fileWriter = new FileWriter(dir+"Chaps Record ("+nowStr+").xml");
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(fileWriter, format);
        writer.write( document );
        writer.close();
    }
}