package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;
import nz.ac.vuw.ecs.swen225.gp22.recorder.ReplayListener;

public class Renderer extends JFrame {

  private int width;
  private int height;
  public BoardPanel panel;

  public Renderer(int w, int h) {
    System.out.println("BREAKPOINT: Renderer created");
    width = w;
    height = h;
  }

  public void setUpGUI() {
    System.out.println("BREAKPOINT: Renderer has set up the GUI.");
    setSize(width, height);
    setTitle("Test Title");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new BoardPanel();
    add(panel);
    setVisible(true);
  }

  public void setUpGUIReplay() {
    System.out.println("BREAKPOINT: Renderer has set up the GUI.");
    setSize(width, height);
    setTitle("Test Title");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new BoardPanel(ReplayListener.currentGame);
    add(panel);
    setVisible(true);
  }

}
