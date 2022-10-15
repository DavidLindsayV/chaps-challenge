package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;
import nz.ac.vuw.ecs.swen225.gp22.domain.AuthenticationColour;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.DomainBuilder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Enemy;
import nz.ac.vuw.ecs.swen225.gp22.domain.Player;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Class to both load and save the level layout from or to xml files.
 * 
 * Student ID: 3005 30113
 * 
 * @author GeorgiaBarrand
 *
 */
public class Parser {

  private static int lastLoadedLevel;
  private static Map<String, Integer> tileTypesLoaded = new HashMap<String, Integer>() {
    {
      put("wall", 0);
      put("exitLock", 0);
      put("exit", 0);
      put("player", 0);
      put("treasure", 0);
      put("enemy", 0);
      put("info", 0);
      put("key", 0);
      put("door", 0);
    }
  };

  /**
   * Load the level layout from an xml file and use it to construct the domain
   * 
   * @param filename the name of the file being parsed
   * @return a Domain object representing the level
   */
  public static Domain loadLevel(String filename) throws DocumentException {
    if (filename == null || !filename.endsWith(".xml")) {
      throw new IllegalArgumentException("Incorrect filename given");
    }

    SAXReader reader = new SAXReader();
    File levelFile = new File("nz/ac/vuw/ecs/swen225/gp22/levels/" + filename);
    Document document = reader.read(levelFile);

    Element levelElement = document.getRootElement();
    if (((Double) levelElement.numberValueOf("@levelNum")).isNaN()) {
      throw new NullPointerException("No level number specified");
    }
    lastLoadedLevel = levelElement.numberValueOf("@levelNum").intValue();
    DomainBuilder builder = new DomainBuilder();

    // Go through each row and load each tile
    for (Element row : levelElement.elements("row")) {
      Number rowNum = row.numberValueOf("@r");
      if (((Double) rowNum).isNaN()) {
        throw new NullPointerException("No row number specified");
      }
      int rowNumInt = rowNum.intValue();
      parseStandardElement(rowNumInt, row.elements("wall"), (r, c) -> builder.wall(r, c), "wall");
      parseStandardElement(rowNumInt, row.elements("exitLock"), (r, c) -> builder.lock(r, c), "exitLock");
      parseStandardElement(rowNumInt, row.elements("player"), (r, c) -> builder.player(r, c), "player");
      parseStandardElement(rowNumInt, row.elements("treasure"), (r, c) -> builder.treasure(r, c), "treasure");
      parseStandardElement(rowNumInt, row.elements("exit"), (r, c) -> builder.exit(r, c), "exit");
      parseStandardElement(rowNumInt, row.elements("info"), (r, c) -> builder.info(r, c), "info");
      parseColourElement(rowNumInt, row.elements("key"), (r, c, colour) -> builder.key(r, c, colour.toUpperCase()),
          "key");
      parseColourElement(rowNumInt, row.elements("door"), (r, c, colour) -> builder.door(r, c, colour.toUpperCase()),
          "door");
      parsePathElement(rowNumInt, row.elements("enemy"), (e) -> builder.enemy(e));

    }
    checkValidDomain();

    Domain d = builder.make();
    // Load any prior collected items
    if (filename.contains("saved_game")) {
      loadItems(d, levelElement.element("items"));
    }
    assert d != null : "Domain is null";
    return d;

  }

  /**
   * Save the current level state to an xml file so that it can be loaded later.
   * 
   * @param domain the current game domain
   * @throws IOException
   */
  public static void saveLevel(Domain domain) throws IOException {
    Tile[][] levelLayout = domain.getInnerState();
    Point player = domain.getPlayerPosition();

    Document document = createLevelDocument(levelLayout, player, domain.getEnemies(), domain);

    String nowStr = getCurrentTime();

    String directory = "nz/ac/vuw/ecs/swen225/gp22/levels/saved_games/saved_game_" + nowStr;
    Path path = Paths.get(directory);
    Files.createDirectory(path);

    Recorder.save(directory + "/");

    FileWriter fileWriter = new FileWriter(directory + "/saved_game_level" + lastLoadedLevel + ".xml");
    OutputFormat format = OutputFormat.createPrettyPrint();
    XMLWriter writer = new XMLWriter(fileWriter, format);
    writer.write(document);
    writer.close();
  }

  /**
   * Check that the level file loaded has provided all the required tiles
   */
  private static void checkValidDomain() {
    if (tileTypesLoaded.get("player") < 1) {
      throw new IllegalArgumentException("No player provided in xml file");
    }
    if (tileTypesLoaded.get("exit") < 1) {
      throw new IllegalArgumentException("No exit provided in xml file");
    }
    if (tileTypesLoaded.get("exitLock") < 1) {
      throw new IllegalArgumentException("No exitLock provided in xml file");
    }
    if (tileTypesLoaded.get("treasure") < 1) {
      throw new IllegalArgumentException("No treasure provided in xml file");
    }
  }

  /**
   * Find and return the current date and time as a string in the format
   * dd-MM-yyyy-HHmmss.
   * 
   * @return current time
   */
  private static String getCurrentTime() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HHmmss");
    LocalDateTime now = LocalDateTime.now();
    String nowStr = dtf.format(now);
    return nowStr;
  }

  /**
   * Create a Document representation of the current level layout.
   * 
   * @param levelLayout 2D array of the positions of tiles on the current level
   * @param playerPos   current player position
   * @param enemies     list of enemies
   * @param d           current domain
   * @return Document representing the current level
   */
  private static Document createLevelDocument(Tile[][] levelLayout, Point playerPos, List<Enemy> enemies, Domain d) {
    Document document = DocumentHelper.createDocument();
    Element level = document.addElement("level").addAttribute("levelNum", "" + lastLoadedLevel);

    for (int row = 0; row < levelLayout.length; row++) {
      Element currRow = level.addElement("row").addAttribute("r", "" + row);
      if (playerPos.row() == row) {
        currRow.addElement("player").addAttribute("c", "" + playerPos.col());
      }
      currRow = addEnemies(currRow, enemies, row);
      currRow = addTiles(levelLayout, row, currRow);
    }
    Element items = level.addElement("items");
    items = addItems(items, d);
    return document;
  }

  /**
   * Add any collected items to the saved file
   * 
   * @param items the items Element
   * @param d     the current domain
   * @return the items Element with added items
   */
  private static Element addItems(Element items, Domain d) {
    Player p = d.getPlayer();
    Map<AuthenticationColour, Integer> keys = p.getKeysCollected();
    
    // Save the initial number of treasures on the level
    items.addAttribute("initialTreasureCount", "" + d.requiredTreasureCount());

    // Save keys
    for (AuthenticationColour colour : keys.keySet()) {
      for (int i = 0; i < keys.get(colour); i++) {
        items.addElement("key").addAttribute("colour", colour.toString());
      }
    }

    // Save treasures
    for (int i = 0; i < p.getTreasureCount(); i++) {
      items.addElement("treasure");
    }

    // Save time
    items.addElement("time").addAttribute("ms", "" + UserListener.getTimeLeft());
    return items;
  }

  /**
   * Add all standard tile to the level document
   * 
   * @param levelLayout the current tile layout
   * @param row         current row number
   * @param currRow     the element representing the row we are constructing for
   *                    the document
   * @return the updated row element
   */
  private static Element addTiles(Tile[][] levelLayout, int row, Element currRow) {
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
    return currRow;
  }

  /**
   * Add all enemies to the level document.
   * 
   * @param currRow the element representing the row we are constructing for the
   *                document
   * @param enemies list of all the enemies on the current level
   * @param row     the current row number
   * @return the updated row element
   */
  private static Element addEnemies(Element currRow, List<Enemy> enemies, int row) {
    if (enemies.isEmpty()) {
      return currRow;
    }
    for (Enemy e : enemies) {
      if (e.getPosition().row() == row) {
        Element enemyElem = currRow.addElement("enemy").addAttribute("c", "" + e.getPosition().col());
        e.getPath().stream().forEach(p -> {
          enemyElem.addElement("path").addAttribute("r", "" + p.row()).addAttribute("c", "" + p.col());
        });
      }
    }
    return currRow;
  }

  /**
   * Parse a standard node which only has a column attribute e.g wall.
   * 
   * @param rowNum   the current row number
   * @param nodes    the tile elements being parsed
   * @param consumer the consumer to build the tile which takes the row and column
   */

  private static void parseStandardElement(int rowNum, List<Element> elems, BiConsumer<Integer, Integer> consumer,
      String tileName) {
    for (Element e : elems) {
      Number colNum = e.numberValueOf("@c");
      if (((Double) colNum).isNaN()) {
        throw new NullPointerException("No col number specified");
      }
      tileTypesLoaded.put(tileName, tileTypesLoaded.get(tileName) + 1);
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
      TriConsumer<Integer, Integer, String> consumer, String tileName) {
    for (Element e : elems) {
      Number colNum = e.numberValueOf("@c");
      if (((Double) colNum).isNaN()) {
        throw new NullPointerException("No col number specified");
      }
      String colour = e.valueOf("@colour");
      if (colour.isEmpty()) {
        throw new NullPointerException("No colour specified");
      }

      tileTypesLoaded.put(tileName, tileTypesLoaded.get(tileName) + 1);

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
  private static void parsePathElement(int rowNum, List<Element> elems, Consumer<Enemy> consumer) {
    // Get the BasicEnemy class from the jar file
	Class<?> basicEnemyClass = ActorLoader.getClass(new File("nz/ac/vuw/ecs/swen225/gp22/levels/level2.jar"),
        "nz.ac.vuw.ecs.swen225.gp22.persistency.BasicEnemy");
    if (basicEnemyClass == null) {
      throw new NullPointerException("No BasicEnemy class found");
    }
    
    // Run through each enemy element
    for (Element e : elems) {
      Number colNum = e.numberValueOf("@c");
      if (((Double) colNum).isNaN()) {
        throw new NullPointerException("No col number specified");
      }
      
      // Create the path
      List<Point> path = new ArrayList<Point>();
      for (Node pathStep : e.elements("path")) {
        Number pathRow = pathStep.numberValueOf("@r");
        Number pathCol = pathStep.numberValueOf("@c");
        if (((Double) pathRow).isNaN() || ((Double) pathCol).isNaN()) {
          throw new NullPointerException("Path point has not been specified");
        }
        path.add(new Point(pathRow.intValue(), pathCol.intValue()));
      }
      if (path.isEmpty()) {
        throw new NullPointerException("Path has not been specified");
      }
      try {
    	// Add the enemy to the domain
        Enemy enemy = (Enemy) basicEnemyClass.getDeclaredConstructor(List.class).newInstance(path);
        consumer.accept(enemy);
      } catch (Exception ex) {
        ex.printStackTrace();
      }

    }
  }

  /**
   * Load any items collected in a previously played game and update the domain
   * 
   * @param d
   * @param items
   */
  private static Domain loadItems(Domain d, Element items) {
    if (items == null) {
      return d;
    }
    Number treasureCount = items.numberValueOf("@initialTreasureCount");
    // Get initial treasure count
    if (((Double) treasureCount).isNaN()) {
      throw new NullPointerException("Treasure count has not been specified");
    }
    d.overrideInitialTreasureCount(treasureCount.intValue());

    Player p = d.getPlayer();
    // Pick up the same amount of treasures as collected previously
    for (int i = 0; i < items.elements("treasure").size(); i++) {
      p.pickUpTreasure();
    }
    
    // Pick up any keys collected previously
    for (Element key : items.elements("key")) {
      String colour = key.valueOf("@colour");
      System.out.println(colour);
      if (colour.isEmpty()) {
        throw new NullPointerException("No colour specified");
      }
      p.addKey(AuthenticationColour.valueOf(colour.toUpperCase()));
    }
    
    // Set the time remaining
    Element time = items.element("time");
    if (((Double) time.numberValueOf("@ms")).isNaN()) {
      throw new NullPointerException("No time specified");
    }
    int msRemaining = time.numberValueOf("@ms").intValue();
    UserListener.timeRemaining = msRemaining;
    return d;
  }

}
