package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.DomainBuilder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Enemy;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

public class Parser {

    private static int lastLoadedLevel;

    /**
     * Load the level layout from an xml file and use it to construct the domain
     * 
     * @param levelNum the level number being parsed
     * @return a Domain object representing the level
     */
    public static Domain loadLevel(String filename) throws DocumentException {
        SAXReader reader = new SAXReader();
        File levelFile = new File("nz/ac/vuw/ecs/swen225/gp22/levels/" + filename);
        Document document = reader.read(levelFile);

        Element levelElement = document.getRootElement();
        lastLoadedLevel = levelElement.numberValueOf("@levelNum").intValue();
        DomainBuilder builder = new DomainBuilder();
        for (Element row : levelElement.elements("row")) {
            Number rowNum = row.numberValueOf("@r");
            if (rowNum == null) {
                throw new NullPointerException("No row number specified");
            }
            int rowNumInt = rowNum.intValue();
            parseStandardNode(rowNumInt, row.elements("wall"), (r, c) -> builder.wall(r, c));
            parseStandardNode(rowNumInt, row.elements("exitLock"),
                    (r, c) -> builder.lock(r, c));
            parseStandardNode(rowNumInt, row.elements("player"),
                    (r, c) -> builder.player(r, c));
            parseStandardNode(rowNumInt, row.elements("treasure"),
                    (r, c) -> builder.treasure(r, c));
            parseStandardNode(rowNumInt, row.elements("exit"),
                    (r, c) -> builder.exit(r, c));
            parseStandardNode(rowNumInt, row.elements("info"),
                    (r, c) -> builder.info(r, c));

            parseColourElement(rowNumInt, row.elements("key"),
                    (r, c, colour) -> builder.key(r, c, colour.toUpperCase()));
            parseColourElement(rowNumInt, row.elements("door"),
                    (r, c, colour) -> builder.door(r, c, colour.toUpperCase()));
            parsePathElement(rowNumInt, row.elements("enemy"),
                    (r, c, path) -> builder.enemy(r.intValue(), c.intValue(), path));

        }

        System.out.println("BREAKPOINT: Domain object created.");
        return builder.make();
    }

    /**
     * Save the current level state to an xml file so that it can be loaded later
     * 
     * @param domain the current game domain
     * @throws IOException
     */
    public static void saveLevel(Domain domain) throws IOException {
        System.out.println("BREAKPOINT: Persistency is saving the level file.");
        Tile[][] levelLayout = domain.getInnerState();
        Point player = domain.getPlayerPosition();

        Document document = createLevelDocument(levelLayout, player, domain.getEnemies());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String nowStr = dtf.format(now);

        String directory = "saved_game_" + nowStr;
        Path path = Paths.get(directory);
        Files.createDirectory(path);

        
        Recorder.save(directory + "/");

        FileWriter fileWriter = new FileWriter(
                directory + "/saved_game_level" + lastLoadedLevel + ".xml");
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(fileWriter, format);
        writer.write(document);
        writer.close();
    }

    /**
     * Create a Document representation of the current level layout
     * 
     * @param levelLayout 2D array of the positions of tiles on the current level
     * @return Document representing the current level
     */
    private static Document createLevelDocument(Tile[][] levelLayout, Point playerPos, List<Enemy> enemies) {
        Document document = DocumentHelper.createDocument();
        Element level = document.addElement("level").addAttribute("levelNum", "" + lastLoadedLevel);

        for (int row = 0; row < levelLayout.length; row++) {
            Element currRow = level.addElement("row").addAttribute("r", "" + row);
            if (playerPos.row() == row) {
                currRow.addElement("player").addAttribute("c", "" + playerPos.col());
            }
            for (Enemy e : enemies) {
                if (e.getPosition().row() == row) {
                    Element enemyElem = currRow.addElement("enemy").addAttribute("c", "" + e.getPosition().col());
                    e.getPath().stream().forEach(p -> {
                        enemyElem.addElement("path").addAttribute("r", "" + p.row()).addAttribute("c", ""
                                + p.col());
                    });
                }
            }
            for (int col = 0; col < levelLayout[0].length; col++) {
                Tile t = levelLayout[row][col];
                String name = t.name();
                if (!name.equals("empty")) {
                    Element tile = currRow.addElement(name).addAttribute("c", "" + col);
                    if (name.equals("door") || name.equals("key")) {
                        tile.addAttribute("colour", t.colour());
                    }
                }
            }
        }
        return document;
    }

    /**
     * Parse a standard node which only has a column attribute e.g wall
     * 
     * @param rowNum   the current row number
     * @param nodes    the tile elements being parsed
     * @param consumer the consumer to build the tile which takes the row and column
     */
    private static void parseStandardNode(int rowNum, List<Element> elems, BiConsumer<Integer, Integer> consumer) {
        for (Element e : elems) {
            Number colNum = e.numberValueOf("@c");
            if (colNum == null) {
                throw new NullPointerException("No col number specified");
            }
            consumer.accept(rowNum, colNum.intValue());
        }
    }

    /**
     * Parse a node tile type which has a colour attribute e.g door, key
     * 
     * @param rowNum   the current row number
     * @param elems    the tile elements being parsed
     * @param consumer the consumer to build the tile which take row, column and the
     *                 colour
     */
    private static void parseColourElement(int rowNum, List<Element> elems,
            TriConsumer<Integer, Integer, String> consumer) {
        for (Element e : elems) {
            Number colNum = e.numberValueOf("@c");
            if (colNum == null) {
                throw new NullPointerException("No col number specified");
            }
            String colour = e.valueOf("@colour");
            if (colour.isEmpty()) {
                throw new IllegalArgumentException("No colour specified");
            }
            consumer.accept(rowNum, colNum.intValue(), colour);
        }
    }

    /**
     * Parse a node tile type which has a path i.e an enemy
     * 
     * @param rowNum   the current row number
     * @param elems    the tile elements being parsed
     * @param consumer the consumer to build the tile which takes the row, column
     *                 and the path
     */
    private static void parsePathElement(int rowNum, List<Element> elems,
            TriConsumer<Integer, Integer, List<Point>> consumer) {
        for (Element e : elems) {
            Number colNum = e.numberValueOf("@c");
            if (colNum == null) {
                throw new NullPointerException("No col number specified");
            }
            List<Point> path = new ArrayList<Point>();
            for (Node pathStep : e.elements("path")) {
                Number pathRow = pathStep.numberValueOf("@r");
                Number pathCol = pathStep.numberValueOf("@c");
                if (pathRow == null || pathCol == null) {
                    throw new NullPointerException("Path point has not been specified");
                }
                path.add(new Point(pathRow.intValue(), pathCol.intValue()));
            }
            consumer.accept(rowNum, colNum.intValue(), path);
        }
    }
}