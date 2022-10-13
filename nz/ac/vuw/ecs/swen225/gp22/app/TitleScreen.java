package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

public class TitleScreen extends JFrame {

  private JButton startGameButton;
  private JPanel panel = new JPanel();

  public TitleScreen() {
    this.setFocusable(true);
    setSize(1000, 1000);
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
    panel.setLayout(null);
    startGameButton.setBounds(350, 200, 300, 80);
    startGameButton.setFont(new Font("Calibri", Font.BOLD, 50));
    startGameButton.setLayout(null);
    panel.setVisible(true);
    this.add(panel);
    JLabel picLabel = new JLabel(new ImageIcon(Img.Title.image));
    this.add(startGameButton);
    this.add(picLabel);
    picLabel.setVerticalAlignment(JLabel.TOP);
    picLabel.setPreferredSize(new Dimension(250, 100));
  }
}
