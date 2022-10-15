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
import javax.swing.event.ChangeListener;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;


/**
 * This class is similar to the APP GUI, and the structure of it is based around it.
 * The class extends the Renderer's JFrame class and adds a menu and buttons
 * to allow display a Replay of a recorded game.
 * 
 * It has the replay options of autoplay, and step by step.
 * 
 * @author Kalani Sheridan - ID: 300527652
 */
public class ReplayGui extends Renderer {

  /**
   * Public fields.
   */
  public static ReplayGui instance;
  public ReplayListener rl;

  /**
   * Private fields.
   */
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
    exit.addActionListener(e -> closeReplay());
    desc.addActionListener(e -> showDesc());

    menuBar.add(menu);
    this.setJMenuBar(menuBar);
    //Add keylistener to JFrame
    this.addKeyListener(rl);
    this.setFocusable(true);
  }

  /**
   * Ends the replay, and removes the buttons to continue the replay.
   */
  public void endOfReplay(){
    JOptionPane.showMessageDialog(frame, "The replay has been completed!");
    this.delPauseButton();
    this.delSpeedSlider();
    this.delStepButton();
    closeReplay();
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
   * Draws game information on the top left, like in the regular APP gui.
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

  /**
   * Creates a new pause button.
   */
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
          ReplayListener.resumeGame(speedSlider.getValue());
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

  /**
   * Deletes a pause button if one exists.
   */
  private void delPauseButton(){
    if(pauseButton != null){
      panel.remove(pauseButton);
    }
  }

  /**
   * Creates a new step/next-tick button.
   */
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

  /**
   * Deletes a step/next-tick button if one exists.
   */
  private void delStepButton(){
    if(stepButton != null){
      panel.remove(stepButton);
    }
  }

  /**
   * Creates a new autoplay speed slider.
   */
  private void actSpeedSlider(){
    sliderLabel = new JLabel();
    speedSlider = new JSlider(100,500,200);
    sliderLabel.setText("Ticks delay in milliseconds = " + speedSlider.getValue());
    panel.setLayout(null);

    sliderLabel.setBounds(400, 25, 200, 50);
    speedSlider.setBounds(400, 75, 200, 50);
    speedSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent ce) {
        ReplayListener.changeTimerSpeed(speedSlider.getValue());
        sliderLabel.setText("Ticks delay in milliseconds = " + speedSlider.getValue());
        repaint();
      }
   });
    panel.add(sliderLabel);
    panel.add(speedSlider);
  }

  /**
   * Deletes a autoplay speed slider if one exists.
   */
  private void delSpeedSlider(){
    if(sliderLabel != null){
      panel.remove(sliderLabel);
    }
    if(speedSlider != null){
      panel.remove(speedSlider);
    }
  }

  /**
   * Sets the replay to be autoplay.
   */
  private void runAutoPlay(){
    delSpeedSlider();
    delPauseButton();
    delStepButton();
    actSpeedSlider();
    actPauseButton();
    ReplayListener.pauseGame();
    pauseButton.setText("▶");
    ReplayListener.setAutoPlay();
  }

  /**
   * Sets the replay to be step by step.
   */
  private void runStepByStep(){
    delSpeedSlider();
    delPauseButton();
    delStepButton();
    actStepButton();
    ReplayListener.setStepByStep();
  }

  /**
   * Closes the replay GUI.
   */
  public static void closeReplay() {
    frame.dispose();
    instance.dispose();
    ReplayListener.stopTimer();
    GUI.closeReplayGui();
  }
}
