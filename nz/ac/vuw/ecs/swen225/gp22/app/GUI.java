package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * This class extends the Renderer's JFrame class and adds a menu and buttons
 * to allow pausing, resuming, saving, loading, and rules displaying
 */
@SuppressWarnings("serial")
public class GUI extends Renderer {

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
  public GUI() {
    super(1000, 1000);
    setUpGUI();
    setLayout(new BorderLayout());
    //Make a JButton pauseButton
    pauseButton = new JButton("⏸");
    pauseButton.setPreferredSize(new Dimension(40, 40));
    pauseButton.addActionListener(
      e -> {
        if (!UserListener.paused) {
          UserListener.pauseGame();
          pauseButton.setText("▶");
        } else {
          UserListener.resumeGame();
          pauseButton.setText("⏸");
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
    //Make exiting, saving and showing rules menu items
    menuBar = new JMenuBar();
    menu = new JMenu("Other Options");
    saveItem = new JMenuItem("Save Level");
    rulesItem = new JMenuItem("Show rules");
    exitItem = new JMenuItem("Exit Game");
    menu.add(exitItem);
    menu.add(saveItem);
    menu.add(rulesItem);
    exitItem.addActionListener(e -> UserListener.exitGame());
    saveItem.addActionListener(e -> UserListener.saveGame());
    rulesItem.addActionListener(e -> showRules());
    menuBar.add(menu);
    this.setJMenuBar(menuBar);
    //Print text onto JFrame

  }

  /**Show the rules panel */
  public void showRules() {
    int xPos = 50;
    int yPos = 50;
    int width = 500;
    int height = 500;
    UserListener.pauseGame();
    pauseButton.setText("▶");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setBounds(xPos, yPos, width, height);
    JTextField rulesField = new JTextField();
    rulesField.setBounds(xPos, yPos, width, height);
    rulesField.setEditable(false);
    rulesField.setText(
      "The rules of the game are simple. Dont lose. You're welcome :-)"
    );
    frame.add(rulesField);
    frame.setVisible(true);
  }

  public static void drawText(Graphics g) {
    g.setFont(new Font("Roboto", Font.BOLD, 20));
    g.setColor(Color.RED);
    g.drawString("Current level: " + UserListener.currentLevel, 50, 50);
    g.setColor(Color.YELLOW);
    g.drawString("Time left: " + pingTimer.timeLeftToPlay, 50, 70);
    g.setColor(Color.GREEN);
    g.drawString(
      "Keys collected: " + UserListener.keysCollected.toString(),
      50,
      90
    );
    g.setColor(Color.BLUE);
    g.drawString("Treasures left: " + UserListener.treasuresLeft, 50, 110);
  }
}
