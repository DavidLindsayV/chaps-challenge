package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

public class ReplayGui extends Renderer {

  public static ReplayGui instance;

  public ReplayListener rl;

  private JButton pauseButton;
  private JButton stepButton;

  private JSlider speedSlider;
  private JLabel sliderLabel;

  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem stepByStep;
  private JMenuItem autoPlay;
  private JMenuItem exit;
  private JMenuItem desc;

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

    activateSpeedSlider();

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

    
    // autoPlay.addActionListener(e -> ReplayListener.autoPlay());
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
  private void showDesc() {
    String descMessage = "The replay with show a replay of a game that has been recorded."
    +"\n\nStep-by-step is manual control of each tick the game goes through."
    +"\n\nAutoplay is when a game is played at a speed spesified by the controling slider.";
    JOptionPane.showMessageDialog(frame, descMessage);
  }

  /**
   * Shows the description of replay
   */
  public static void drawText(Graphics g) {
    g.setFont(new Font("Roboto", Font.BOLD, 20));
    g.setColor(Color.PINK);
    g.drawString("Replay: Step by Step", 50, 50);
    g.setColor(Color.RED);
    g.drawString("Current level: " + ReplayListener.currentLevel, 50, 70);
    g.setColor(Color.YELLOW);
    g.drawString("Time left: FIX LATER", 50, 90);
    g.setColor(Color.GREEN);
    g.drawString(
      "Keys collected: " + ReplayListener.currentGame.keysCollected(),
      50,
      110
    );
    g.setColor(Color.BLUE);
    g.drawString(
      "Treasures left: " + ReplayListener.currentGame.treasuresLeft(),
      50,
      130
    );
  }

  private void activatePauseButton(){
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
  }

  private void activateStepButton(){
    //Make a JButton pauseButton
    stepButton = new JButton("Next Tick!");
    stepButton.setPreferredSize(new Dimension(40, 40));
    stepButton.addActionListener( e -> ReplayListener.nextMove() );
    panel.setLayout(null);
    stepButton.setFont(
      new Font(stepButton.getFont().getName(), Font.PLAIN, 40)
    );
    stepButton.setMargin(new Insets(0, 0, 0, 0));
    stepButton.setBounds(900, 50, 50, 50);
    panel.add(stepButton);
  }

  private void activateSpeedSlider(){
    
    sliderLabel = new JLabel();

    speedSlider = new JSlider(0,10,5);

    sliderLabel.setText("Ticks per second = " + speedSlider.getValue());

    panel.add(sliderLabel);
    panel.add(speedSlider);

  }

  public void updateSlider(ChangeEvent e) {
    sliderLabel.setText("Ticks per second = " + speedSlider.getValue());
  }

  public static void closeAll() {
    frame.dispose();
    instance.dispose();
  }

}
