package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.SwingUtilities;

/**
 * An executable class which starts the game
 */
public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GUI());
  }
}
