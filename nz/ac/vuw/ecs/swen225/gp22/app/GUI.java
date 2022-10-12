package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * This class extends the Renderer's JFrame class and adds a menu and buttons
 * to allow pausing, resuming, saving, loading, and rules displaying
 */
@SuppressWarnings("serial")
public class GUI extends Renderer {

  //Is a Singleton to allow static access to this gui
  public static GUI instance;
  //Is the KeyListener of the user which will listen and react to keypresses
  public UserListener listener;

  //A button for pausing the game
  JButton pauseButton;
  //The menu and its elements
  JMenuBar menuBar;
  JMenu menu;
  JMenuItem exitItem;
  JMenuItem saveItem;
  JMenuItem rulesItem;
  JMenuItem recordPlayerItem;
  JMenuItem playSavedItem;

  //The rules text
  String rulesText =
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
  ReplayGUI replayGUI;

  /**Makes the GUI for saving, loading, pausing and other functionality */
  public GUI() {
    super(1000, 1000);
    instance = this;
    System.out.println("BREAKPOINT: Creating user listener....");
    listener = new UserListener();
    setUpGUI();
    setLayout(new BorderLayout());
    //Make a JButton pauseButton
    pauseButton = new JButton("⏸");
    pauseButton.setPreferredSize(new Dimension(40, 40));
    pauseButton.addActionListener(
      e -> {
        if (!UserListener.paused) {
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
    menu = new JMenu("Other Options");
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
    playSavedItem.addActionListener(e -> UserListener.loadSavedGame());
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
        replayGUI = new ReplayGUI();
      }
    );
  }

  /**Show the rules panel */
  private void showRules() {
    pauseGame();
    JOptionPane.showMessageDialog(this, rulesText);
  }

  public static void showToolTip(String toolTip) {
    JOptionPane.showMessageDialog(GUI.instance, toolTip);
  }

  public static void removeToolTip() {
    JOptionPane.getRootFrame().dispose();
  }

  /**A function that draws various texts, such as current level and keys collected, on the JPanel */
  public static void drawText(Graphics g) {
    g.setFont(new Font("Roboto", Font.BOLD, 20));
    g.setColor(Color.RED);
    g.drawString(
      "Current level: " + shortenLevelName(UserListener.currentLevel),
      50,
      50
    );
    g.setColor(Color.YELLOW);
    g.drawString("Time left: " + pingTimer.timeLeftToPlay / 1000, 50, 70);
    g.setColor(Color.GREEN);
    g.drawString(
      "Keys collected: " + UserListener.currentGame.keysCollected(),
      50,
      90
    );
    g.setColor(Color.BLUE);
    g.drawString(
      "Carrots left: " + UserListener.currentGame.treasuresLeft(),
      50,
      110
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
  }

  /**A function to close all the JFrames */
  public static void closeAll() {
    instance.dispose();
  }

  public static String shortenLevelName(String levelName) {
    return levelName.substring(
      Math.max(0, levelName.lastIndexOf("/") + 1),
      levelName.length() - 4
    );
  }
}
