package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

public class ReplayGui extends Renderer {

  public static ReplayGui instance;

  public ReplayListener rl;

  JButton pauseButton;
  JMenuBar menuBar;
  JMenu menu;
  JMenuItem stepByStep;
  JMenuItem autoPlay;
  JMenuItem exit;
  JMenuItem desc;

  static final JFrame frame = new JFrame();

  /**
   * Makes the GUI for saving, loading, pausing and other functionality 
   */
  public ReplayGui() {
    super(1000, 1000);
    instance = this;
    System.out.println("REPLAY GUI: Creating user listener....");
    rl = new ReplayListener();

    setUpGUIReplay();
    
    setLayout(new BorderLayout());
    //Make a JButton pauseButton
    pauseButton = new JButton("⏸");
    pauseButton.setPreferredSize(new Dimension(40, 40));
    pauseButton.addActionListener(
      e -> {
        if (!ReplayListener.paused) {
          ReplayListener.pauseGame();
          pauseButton.setText("▶");
        } else {
          ReplayListener.resumeGame();
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
    
    stepByStep = new JMenuItem("Step by step");
    autoPlay = new JMenuItem("Auto Replay");
    desc = new JMenuItem("Description");
    exit = new JMenuItem("Exit Game");

    menu.add(stepByStep);
    menu.add(autoPlay);
    menu.add(desc);
    menu.add(exit);

    
    //autoPlay.addActionListener(e -> ReplayListener.autoPlay());
    // stepByStep.addActionListener(e -> ReplayListener.stepByStep());
    exit.addActionListener(e -> ReplayListener.exitGame());
    desc.addActionListener(e -> showDesc());

    menuBar.add(menu);
    this.setJMenuBar(menuBar);
    //Add keylistener to JFrame
    this.addKeyListener(rl);
    this.setFocusable(true);

    System.out.println("BREAKPOINT: Keys are listening...");
  }

  /**
   * Shows the description of replay
   */
  public void showDesc() {
    int xPos = 50;
    int yPos = 50;
    int width = 500;
    int height = 500;
    ReplayListener.pauseGame();
    pauseButton.setText("▶");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setBounds(xPos, yPos, width, height);
    JTextField descField = new JTextField();
    descField.setBounds(xPos, yPos, width, height);
    descField.setEditable(false);
    descField.setText(
      "The replay with show a replay of a game that has been recorded."
      +"\nStep-by-step is manual control of each tick the game goes through"
      +"\nAutoplay is when a game is played at a speed spesified by the controling slider"
    );
    frame.add(descField);
    frame.setVisible(true);
  }

  /**
   * Shows the description of replay
   */
  public static void drawText(Graphics g) {
    g.setFont(new Font("Roboto", Font.BOLD, 20));
    g.setColor(Color.RED);
    g.drawString("Current level: " + ReplayListener.currentLevel, 50, 50);
    g.setColor(Color.YELLOW);
    g.drawString("Time left: FIX LATER", 50, 70);
    g.setColor(Color.GREEN);
    g.drawString(
      "Keys collected: " + ReplayListener.currentGame.keysCollected(),
      50,
      90
    );
    g.setColor(Color.BLUE);
    g.drawString(
      "Treasures left: " + ReplayListener.currentGame.treasuresLeft(),
      50,
      110
    );
  }

  public static void closeAll() {
    frame.dispose();
    instance.dispose();
  }
}
