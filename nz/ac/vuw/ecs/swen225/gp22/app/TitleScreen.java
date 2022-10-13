package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp22.renderer.BoardPanel;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

public class TitleScreen extends JFrame {

  private JButton startGameButton;
  private BoardPanel panel;

  public TitleScreen() {
    this.setFocusable(true);
    setSize(1000, 1000);
    panel
      .getGraphics()
      .drawImage(
        Img.Title.image,
        0,
        0,
        1000,
        1000,
        0,
        0,
        Img.Title.image.getWidth(),
        Img.Title.image.getHeight(),
        null
      );
    setTitle("Title Screen");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    startGameButton = new JButton("Start Game!");
    startGameButton.addActionListener(
      e -> {
        TitleScreen.this.dispose();
        SwingUtilities.invokeLater(
          () -> {
            new GUI();
          }
        );
      }
    );
    //startGameButton.setPreferredSize(new Dimension(200, 100));
    panel.setLayout(null);
    startGameButton.setBounds(400, 500, 200, 100);
    startGameButton.setLayout(null);
    panel.add(startGameButton);
    panel.setVisible(true);
    this.add(panel);
  }

  @Override
  public void paint(Graphics g) {}
}
