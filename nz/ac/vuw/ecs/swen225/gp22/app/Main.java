package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;

/**
 * An executable class which starts the game
 * @author David Lindsay 300584648
 */
public class Main {

  //A field which stores the JFrame the game runs/displays through
  public static GUI gui;

  /** Starts the game
   * @param args
   */
  public static void main(String[] args) {
    SoundEffects.playSound("music");
    SwingUtilities.invokeLater(
      () -> {
        System.out.println("BREAKPOINT: Creating GUI....");
        gui = new GUI();
      }
    );
  }
}
