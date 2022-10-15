package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * This is the class we will be using to write (save) xml files
 * 
 * @author Kalani Sheridan - ID: 300527652  
 */
public class RecordWriter {

    /* 
     * Fields. 
     */
    private Document document;
    private Element game;
    private int tickNum;


    /**
     * Recorder constructor.
     * 
     * @param document - The dom4j document we are writing too.
     * @param level - The level xml name.
     */
    public RecordWriter(Document document, String level) {
        this.document = document;
        this.game = this.document.addElement("game");
        this.game.addElement("level")
                 .addText(level);
        this.tickNum = 0;
    }


    /**
     * Records a tick and what happens within the tick.
     * 
     * @param <E> - The generic enum we are using.
     * @param move - The move that is being recorded.
     */
    public <E extends Enum<E>> void tick(E move) {
        Element tick = this.game.addElement("tick")
                .addAttribute("tick", this.tickNum + "");

        this.move(tick, "player", move.name());
        tickNum++;
    }


    /**
     * Record a given move.
     *
     * @param tick - The dom4j element we have formatted to be the tick.
     * @param actor - The actor that is doing the move.
     * @param move - The move that is being recorded in this tick.
     */
    private void move(Element tick, String actor, String move) {
        tick.addElement("move")
                .addAttribute("actor", actor)
                .addText(move);
    }


    /**
     * Saves the recorded game.
     * 
     * @param dir - THe directory of where we are saving the game.
     * @throws IOException
     */
    public void save(String dir) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String nowStr = dtf.format(now);
        document.addComment(nowStr);
        // Pretty print write to a xml file
        FileWriter fileWriter = new FileWriter(dir + "game_record_" + nowStr + ".xml");
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(fileWriter, format);
        writer.write(document);
        writer.close();
    }
}