package nz.ac.vuw.ecs.swen225.gp22.app;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

import javax.swing.SwingUtilities;

/**
 * An executable class which starts the game
 */
public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() ->new GUI());
  }
}
