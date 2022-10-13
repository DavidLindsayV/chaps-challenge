package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import nz.ac.vuw.ecs.swen225.gp22.recorder.ReplayGui;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

/**
 * This class extends the Renderer's JFrame class and adds a menu and buttons
 * to allow pausing, resuming, saving, loading, and rules displaying
 * @author David Lindsay 300584648
 */
@SuppressWarnings("serial")
public class GUI extends Renderer {

  //Is a Singleton to allow static access to this gui
  public static GUI instance;
  //Is the KeyListener of the user which will listen and react to keypresses
  public UserListener listener;

  //A button for pausing the game
  private JButton pauseButton;
  //The menu and its elements
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem exitItem; //The menu item that will exit the game
  private JMenuItem saveItem; //the menu item that will save the game
  private JMenuItem rulesItem; //the menu item that will display the rules
  private JMenuItem recordPlayerItem; //the menu item that will play a recorded game
  private JMenuItem playSavedItem; //the menu item that will play a saved game

  MetalLookAndFeel metal;

  //The rules text
  private String rulesText =
    "You're a little rabbit, try and navigate through the maze and collect all the carrots before time runs out!\n" +
    "\n" +
    "Controls:" +
    "- Up, down, left and right arrow keys move the rabbit\n" +
    "- Ctrl-X exits the game\n" +
    "- Ctrl-S saves and exits the game\n" +
    "- Ctrl-R resumes a saved game\n" +
    "- Ctrl-1 and Ctrl-2 start games at level 1 and level 2\n" +
    "- Space to Pause game, Esc to Play game (as well as the pause/play button\n" +
    "- There are menu items for showing rules, saving, exiting, and showing recorded levels\n" +
    "\n" +
    "Core game mechanics:\n" +
    "- Collect all the carrots and walk down the rabbit hole to win\n" +
    "- Collect keys to open doors of their respective colours\n" +
    "- Avoid colliding with enemies\n";

  //A field to store the JFrame for replaying recorded levels
  private static ReplayGui replayGUI;

  /**
   * Adds the key listener, creates the buttons and creates the JMenu
   */
  public GUI() {
    super(1000, 1000);
    setUpGUI();
    instance = this;
    metal = new MetalLookAndFeel();
    MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
    try {
      UIManager.setLookAndFeel(metal);
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    System.out.println("BREAKPOINT: Creating user listener....");
    listener = new UserListener();
    setLayout(new BorderLayout());
    //Make a JButton pauseButton
    pauseButton = new JButton("⏸");
    pauseButton.setPreferredSize(new Dimension(40, 40));
    pauseButton.addActionListener(
      e -> {
        if (!UserListener.paused()) {
          pauseGame();
        } else {
          resumeGame();
        }
      }
    );
    panel.setLayout(null);
    pauseButton.setFont(
      new Font(pauseButton.getFont().getName(), Font.PLAIN, 40)
    );
    pauseButton.setMargin(new Insets(0, 0, 0, 0));
    pauseButton.setBounds(900, 50, 50, 50);
    panel.add(pauseButton);
    //Make exiting, saving showing rules and record playing menu items
    menuBar = new JMenuBar();
    menuBar.setBorder(
      BorderFactory.createCompoundBorder(
        menuBar.getBorder(),
        BorderFactory.createEmptyBorder(0, 0, 0, 0)
      )
    );
    menu = new JMenu("Menu Items");
    menu.setFont(new Font("Roboto", Font.BOLD, 20));
    saveItem = new JMenuItem("Save Level");
    rulesItem = new JMenuItem("Show rules");
    exitItem = new JMenuItem("Exit Game");
    recordPlayerItem = new JMenuItem("Play recorded game");
    playSavedItem = new JMenuItem("Load saved game");
    menu.add(exitItem);
    menu.add(saveItem);
    menu.add(rulesItem);
    menu.add(recordPlayerItem);
    menu.add(playSavedItem);
    exitItem.addActionListener(e -> UserListener.exitGame());
    saveItem.addActionListener(
      e -> {
        pauseGame();
        UserListener.saveGame();
      }
    );
    rulesItem.addActionListener(e -> showRules());
    recordPlayerItem.addActionListener(e -> playRecord());
    playSavedItem.addActionListener(
      e -> {
        pauseGame();
        UserListener.loadSavedGame();
      }
    );
    menuBar.add(menu);
    this.setJMenuBar(menuBar);
    //Add keylistener to JFrame
    this.addKeyListener(listener);
    this.setFocusable(true);
    System.out.println("BREAKPOINT: Keys are listening...");
  }

  /**Plays a recorded game */
  private void playRecord() {
    pauseGame();
    SwingUtilities.invokeLater(
      () -> {
        replayGUI = new ReplayGui();
      }
    );
  }

  /**Show the rules panel */
  private void showRules() {
    pauseGame();
    JOptionPane.showMessageDialog(this, rulesText);
  }

  /** Used by Domain to show tool tips
   * @param toolTip
   */
  public static void showToolTip(String toolTip) {
    JOptionPane.showMessageDialog(GUI.instance, toolTip);
  }

  /**
   * Removes all existing tool tips
   */
  public static void removeToolTip() {
    JOptionPane.getRootFrame().dispose();
  }

  /** A function that draws various texts, such as current level and keys collected, on the JPanel
   * @param g
   */
  public static void drawText(Graphics g) {
    g.setColor(new Color(50, 50, 50));
    g.fillRect(200, 25, 400, 110);
    g.setFont(new Font("Roboto", Font.BOLD, 30));
    g.setColor(Color.RED);
    g.drawString(
      "Current level: " + shortenLevelName(UserListener.currentLevel()),
      200,
      50
    );
    g.setColor(Color.YELLOW);
    g.drawString(
      "Time left: " + UserListener.timer().timeLeftToPlay() / 1000,
      200,
      75
    );
    g.setColor(Color.GREEN);
    g.drawString(
      "Keys collected: " + UserListener.currentGame.keysCollected(),
      200,
      100
    );
    g.setColor(Color.BLUE);
    g.drawString("Carrots left: ", 200, 125);
    int x = 370;
    for (int i = 0; i < UserListener.currentGame.treasuresLeft(); i++) {
      drawImage(Img.TreasureT.image, x, 105, g);
      x += 25;
    }
  }

  private static void drawImage(BufferedImage img, int x, int y, Graphics g) {
    g.drawImage(
      img,
      x,
      y,
      x + 25,
      y + 25,
      0,
      0,
      img.getWidth(),
      img.getHeight(),
      null
    );
  }

  /**A function to change the text of the pause/play button before calling pauseGame in UserListener */
  private void pauseGame() {
    pauseButton.setText("▶");
    UserListener.pauseGame();
  }

  /**A function to change the text of the pause/play button before calling resumeGame in UserListener */
  private void resumeGame() {
    pauseButton.setText("⏸");
    UserListener.resumeGame();
    this.requestFocus();
  }

  /**A function to close all the JFrames */
  public static void closeAll() {
    instance.dispose();
  }

  /** A function that processes the level file path, shortening it for displaying
   * @param levelName
   * @return shortened level name
   */
  public static String shortenLevelName(String levelName) {
    if (levelName != null) {
      return levelName.substring(
        Math.max(0, levelName.lastIndexOf("/") + 1),
        levelName.length() - 4
      );
    } else {
      return "";
    }
  }

  /** Getter for replayGui
   * @return replayGui
   */
  public static ReplayGui replayGui() {
    return replayGUI;
  }

  /**
   * Close the replay gui
   */
  public static void closeReplayGui() {
    replayGUI = null;
  }
}
