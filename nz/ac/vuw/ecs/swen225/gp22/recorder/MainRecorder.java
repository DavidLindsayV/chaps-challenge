package nz.ac.vuw.ecs.swen225.gp22.recorder;

import javax.swing.SwingUtilities;

/**
 * An executable class which starts the game recorder for debugging
 */
public class MainRecorder {

  public static ReplayGui gui;

  public static void main(String[] args) {

    SwingUtilities.invokeLater(
      () -> {
        System.out.println("BREAKPOINT: Creating GUI....");
        gui = new ReplayGui();
      }
    );

    // MockRecorder.run();
  }
}
