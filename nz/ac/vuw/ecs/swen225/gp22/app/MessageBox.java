package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

/**
 * A class for showing JOptionPanes with a certain style
 * @author David Lindsay 300584648
 */
public class MessageBox {

  public static void showMessage(String title, String message) {
    JOptionPane.showMessageDialog(
      GUI.instance,
      message,
      title,
      JOptionPane.INFORMATION_MESSAGE,
      new ImageIcon(Img.TreasureT.image)
    );
  }

  public static void closeMessages(){
    JOptionPane.getRootFrame().dispose();
  }
}
