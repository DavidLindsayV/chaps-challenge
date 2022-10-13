package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;
import nz.ac.vuw.ecs.swen225.gp22.recorder.ReplayListener;

/**
 * sets up the GUI and regins rendering.
 * @author Adam Goodyear 300575240
 */


public class Renderer extends JFrame {

  private int width;
  private int height;
  public BoardPanel panel;

  /**
  * constructor for renderer
  * 
  * @param width renderer width
  * @param width renderer height
  */
  public Renderer(int w, int h) {
    width = w;
    height = h;
  }

  /**
  * Set up function sets up the GUI
  */
  public void setUpGUI() {
    setSize(width, height);
    setTitle("Test Title");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new BoardPanel();
    add(panel);
    setVisible(true);
  }
}
