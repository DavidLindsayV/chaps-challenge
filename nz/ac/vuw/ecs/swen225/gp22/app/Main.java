package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;

/**
 * An executable class which starts the game
 */
public class Main {

  public static GUI gui;

  public static void main(String[] args) {
    SoundEffects s = new SoundEffects();
    s.playSound("music");
    SwingUtilities.invokeLater(
        () -> {
          System.out.println("BREAKPOINT: Creating GUI....");
          gui = new GUI();
        });
  }
}
