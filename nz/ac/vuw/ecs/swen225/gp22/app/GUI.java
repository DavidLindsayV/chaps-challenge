package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import nz.ac.vuw.ecs.swen225.gp22.domain.AuthenticationColour;
import nz.ac.vuw.ecs.swen225.gp22.recorder.ReplayGui;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

/**
 * This class extends the Renderer's JFrame class and adds a menu and buttons to
 * allow pausing, resuming, saving, loading, and rules displaying
 *
 * @author David Lindsay 300584648
 */
@SuppressWarnings("serial")
public class GUI extends Renderer {

  // Is a Singleton to allow static access to this gui
  public static GUI instance;
  // Is the KeyListener of the user which will listen and react to keypresses
  public UserListener listener;

  // A button for pausing the game
  private JButton pauseButton;
  // The menu and its elements
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem exitItem; // The menu item that will exit the game
  private JMenuItem saveItem; // the menu item that will save the game
  private JMenuItem rulesItem; // the menu item that will display the rules
  private JMenuItem recordPlayerItem; // the menu item that will play a recorded game
  private JMenuItem playSavedItem; // the menu item that will play a saved game

  //Metal loook for the jmenubar
  LookAndFeel lookAndFeel;

  // A field to store the JFrame for replaying recorded levels
  private static ReplayGui replayGUI;

  /**
   * Adds the key listener, creates the buttons and creates the JMenu
   */
  public GUI() {
    super(1000, 1000);
    setUpGUI();
    instance = this;
    lookAndFeel = new MetalLookAndFeel();
    MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
    try {
      UIManager.setLookAndFeel(lookAndFeel);
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    listener = new UserListener();
    setLayout(new BorderLayout());
    // Make a JButton pauseButton
    pauseButton = new JButton("⏸");
    pauseButton.setPreferredSize(new Dimension(40, 40));
    pauseButton.addActionListener(
      e -> {
        if (!UserListener.paused()) {
          pauseGame(true);
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
    // Make exiting, saving showing rules and record playing menu items
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
        UserListener.saveGame();
      }
    );
    rulesItem.addActionListener(e -> showRules());
    recordPlayerItem.addActionListener(
      e -> {
        pauseGame(false);
        playRecord();
      }
    );
    playSavedItem.addActionListener(
      e -> {
        pauseGame(false);
        UserListener.loadSavedGame();
      }
    );
    menuBar.add(menu);
    this.setJMenuBar(menuBar);
    // Add keylistener to JFrame
    this.addKeyListener(listener);
    this.setFocusable(true);
  }

  /** Plays a recorded game */
  private void playRecord() {
    pauseGame(false);
    SwingUtilities.invokeLater(
      () -> {
        replayGUI = new ReplayGui();
      }
    );
  }

  /** Show the rules panel */
  private void showRules() {
    pauseGame(true);
    rulesDisplayer.showRules();
  }

  /**
   * Used by Domain to show tool tips
   *
   * @param toolTip
   */
  public static void showToolTip(String toolTip) {
    redrawJFrame();
    MessageBox.showMessage("Tool tip", toolTip);
  }

  /**
   * Removes all existing tool tips
   */
  public static void removeToolTip() {
    MessageBox.closeMessages();
  }

  /**
   * A function that draws various texts, such as current level and keys
   * collected, on the JPanel
   *
   * @param g
   */
  public static void drawText(Graphics g) {
    g.setColor(new Color(50, 50, 50));
    g.fillRect(200, 25, 600, 110);
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
    //Draw the keys collected
    g.setColor(Color.GREEN);
    g.drawString("Keys collected: ", 200, 100);
    Map<AuthenticationColour, Integer> keyMap = UserListener.currentGame.keysHistory();
    int x = 430;
    if (keyMap != null) {
      for (AuthenticationColour c : keyMap.keySet()) {
        for (int i = 0; i < keyMap.get(c); i++) {
          drawImage(keyToImg(c), x, 75, g);
          x += 25;
        }
      }
    }
    //Draw carrots left to collect
    g.setColor(Color.BLUE);
    g.drawString("Carrots left: ", 200, 125);
    x = 370;
    for (int i = 0; i < UserListener.currentGame.treasuresLeft(); i++) {
      drawImage(Img.TreasureT.image, x, 105, g);
      x += 25;
    }
  }

  /**Converts from key colour to corresponding image
   * @param c
   * @return
   */
  private static BufferedImage keyToImg(AuthenticationColour c) {
    if (c == AuthenticationColour.PINK) {
      return Img.RedKeyT.image;
    } else if (c == AuthenticationColour.BLUE) {
      return Img.BlueKeyT.image;
    } else if (c == AuthenticationColour.GREEN) {
      return Img.GreenKeyT.image;
    } else {
      return Img.YellowKeyT.image;
    }
  }

  /**Draws an image on the jframe
   * @param img
   * @param x
   * @param y
   * @param g
   */
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

  /**
   * A function to change the text of the pause/play button before calling
   * pauseGame in UserListener
   */
  public void pauseGame(boolean showMessage) {
    if (pauseButton != null) {
      pauseButton.setText("▶");
      UserListener.pauseGame(showMessage);
    }
  }

  /**
   * A function to change the text of the pause/play button before calling
   * resumeGame in UserListener
   */
  public void resumeGame() {
    pauseButton.setText("⏸");
    UserListener.resumeGame();
    this.requestFocus();
  }

  /** A function to close all the JFrames */
  public static void closeAll() {
    instance.dispose();
  }

  /**
   * A function that processes the level file path, shortening it for displaying
   *
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

  /**
   * Getter for replayGui
   *
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

  /**
   * Redraws the JFrame of GUI
   */
  public static void redrawJFrame() {
    if (GUI.instance != null) {
      GUI.instance.panel.revalidate();
      GUI.instance.panel.repaint();
    }
  }
}
