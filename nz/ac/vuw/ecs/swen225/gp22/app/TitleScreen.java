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

public class TitleScreen extends JFrame {

  private JButton startGameButton;
  private JPanel panel;

  public TitleScreen() {
    this.setFocusable(true);
    setSize(1000, 1000);
    final BufferedImage image;
    try {
      image =
        ImageIO.read(
          new File("nz/ac/vuw/ecs/swen225/gp22/renderer/Sprites/Title.png")
        );
      panel =
        new JPanel() {
          @Override
          protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
          }
        };
    } catch (IOException e) {}
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
