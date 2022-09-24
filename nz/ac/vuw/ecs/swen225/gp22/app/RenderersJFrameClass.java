package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderersJFrameClass extends JFrame {

  private int width;
  private int height;
  protected JPanel panel;

  public RenderersJFrameClass(int w, int h) {
    width = w;
    height = h;
  }

  public void setUpGUI() {
    setSize(width, height);
    setTitle("Test Title");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel();
    add(panel);
    setVisible(true);
  }
}
