package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 * This class extends the Renderer's JFrame class and adds a menu and buttons
 * to allow pausing, resuming, saving, loading, and rules displaying
 */
@SuppressWarnings("serial")
public class GUI extends RenderersJFrameClass {

  static JFrame gameFrame;
  UserListener ul = new UserListener();

  JButton pauseButton;
  JMenuBar menuBar;
  JMenu menu;
  JMenuItem exitItem;
  JMenuItem saveItem;
  JMenuItem rulesItem;

  static final JFrame frame = new JFrame();
  JTextField rulesField;

  /**Makes the GUI for saving, loading, pausing and other functionality */
  public GUI(String title) {
    //Make a JButton pauseButton
    pauseButton = new JButton("pause button");
    gameFrame.add(pauseButton);
    pauseButton.addActionListener(
      e -> {
        if (!UserListener.paused) {
          UserListener.pauseGame();
        } else {
          UserListener.resumeGame();
        }
      }
    );
    //Make exiting, saving and showing rules menu items
    menuBar = new JMenuBar();
    gameFrame.setJMenuBar(menuBar);
    menu = new JMenu("Buttons menu");
    exitItem = new JMenuItem("Exit");
    saveItem = new JMenuItem("Save");
    rulesItem = new JMenuItem("Show rules");
    menu.add(exitItem);
    menu.add(saveItem);
    menu.add(rulesItem);
    exitItem.addActionListener(e -> UserListener.exitGame());
    saveItem.addActionListener(e -> UserListener.saveGame());
    rulesItem.addActionListener(e -> showRules());
  }

  /**Show the rules panel */
  public static void showRules() {
    UserListener.pauseGame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setBounds(50, 50, 150, 200);
    JTextField rulesField = new JTextField();
    rulesField.setBounds(50, 50, 150, 200);
    rulesField.setEditable(false);
    rulesField.setText(
      "The rules of the game are simple. Dont lose. You're welcome :-)"
    );
    frame.add(rulesField);
  }
}
