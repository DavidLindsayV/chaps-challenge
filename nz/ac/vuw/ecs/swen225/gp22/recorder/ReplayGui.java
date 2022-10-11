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

    setUpGUI();
    setLayout(new BorderLayout());

    runStepByStep();

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

    
    autoPlay.addActionListener(e -> runAutoPlay());
    stepByStep.addActionListener(e -> runStepByStep());
    exit.addActionListener(e -> ReplayListener.exitGame());
    desc.addActionListener(e -> showDesc());

    menuBar.add(menu);
    this.setJMenuBar(menuBar);
    //Add keylistener to JFrame
    this.addKeyListener(rl);
    this.setFocusable(true);
    System.out.println("REPLAY GUI: Keys are listening...");
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
    g.drawString("Time left: "+ReplayListener.displayTime , 50, 90);
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

  private void actPauseButton(){
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

  private void delPauseButton(){
    if(pauseButton != null){
      panel.remove(pauseButton);
    }
  }

  private void actStepButton(){
    stepButton = new JButton("Next Tick!");
    stepButton.setPreferredSize(new Dimension(100, 40));
    stepButton.addActionListener( e -> ReplayListener.nextMove() );
    panel.setLayout(null);
    stepButton.setFont(
      new Font(stepButton.getFont().getName(), Font.PLAIN, 20)
    );
    stepButton.setMargin(new Insets(0, 0, 0, 0));
    stepButton.setBounds(800, 50, 100, 50);
    panel.add(stepButton);
  }
  private void delStepButton(){
    if(stepButton != null){
      panel.remove(stepButton);
    }
  }

  private void actSpeedSlider(){
    sliderLabel = new JLabel();
    speedSlider = new JSlider(0,400,200);
    sliderLabel.setText("Ticks per second = " + speedSlider.getValue());
    panel.setLayout(null);
    speedSlider.setBounds(400, 50, 200, 50);
    panel.add(sliderLabel);
    panel.add(speedSlider);
  }
  private void delSpeedSlider(){
    if(sliderLabel != null){
      panel.remove(sliderLabel);
    }
    if(speedSlider != null){
      panel.remove(speedSlider);
    }
  }

  private void updateSlider(ChangeEvent e) {
    sliderLabel.setText("Ticks per second = " + speedSlider.getValue());
  }

  private void runAutoPlay(){
    System.out.println("Running autoplay");
    delStepButton();
    actSpeedSlider();
    actPauseButton();
    ReplayListener.setAutoPlay();
  }

  private void runStepByStep(){
    System.out.println("Running step by step");
    delSpeedSlider();
    delPauseButton();
    actStepButton();
    ReplayListener.setStepByStep();
  }

  public static void closeAll() {
    frame.dispose();
    instance.dispose();
  }

}
