package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MessageBox {

  public static void showMessage(
    String message,
    String title,
    BufferedImage img
  ) {
    JOptionPane.showMessageDialog(
      GUI.instance,
      message,
      title,
      JOptionPane.INFORMATION_MESSAGE,
      new ImageIcon(img)
    );
  }
}
