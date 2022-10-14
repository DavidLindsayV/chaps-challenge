package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

/**
 * A class to display the title screen
 * @author David Lindsay 300584648
 */
public class TitleScreen extends JFrame {

  //Button to start the game
  private JButton startGameButton;
  //Button to show the rules
  private JButton rulesButton;
  //Button to quit the game
  private JButton quitButton;
  //J panel to display stuff
  private JPanel panel = new JPanel();

  /**
   * Displays a Jfame with an image as the title screen
   */
  public TitleScreen() {
    try {
      UIManager.setLookAndFeel(new NimbusLookAndFeel());
    } catch (UnsupportedLookAndFeelException e1) {
      System.out.println("Error loading title screen");
    }

    this.setFocusable(true);
    setSize(1000, 1000);
    setTitle("Title Screen");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    addButton(
      startGameButton,
      "Start Game!",
      e -> {
        TitleScreen.this.dispose();
        SwingUtilities.invokeLater(
          () -> {
            new GUI();
          }
        );
      },
      350,
      200,
      300,
      80,
      new Font("Calibri", Font.BOLD, 50)
    );
    addButton(
      rulesButton,
      "Rules",
      e -> rulesDisplayer.showRules(),
      350,
      300,
      140,
      70,
      new Font("Calibri", Font.BOLD, 35)
    );
    addButton(
      quitButton,
      "Quit",
      e -> this.dispose(),
      510,
      300,
      140,
      70,
      new Font("Calibri", Font.BOLD, 35)
    );
    panel.setLayout(null);
    panel.setVisible(true);
    this.add(panel);
    JLabel picLabel = new JLabel(new ImageIcon(Img.Title.image));
    this.add(picLabel);
    picLabel.setVerticalAlignment(JLabel.TOP);
    picLabel.setPreferredSize(new Dimension(250, 100));
  }

  private void addButton(
    JButton button,
    String buttonText,
    ActionListener action,
    int x,
    int y,
    int width,
    int height,
    Font buttonFont
  ) {
    button = new JButton(buttonText);
    button.addActionListener(action);
    button.setBounds(x, y, width, height);
    button.setFont(buttonFont);
    button.setLayout(null);
    button.setVerticalAlignment(SwingConstants.CENTER);
    button.setVerticalTextPosition(SwingConstants.CENTER);
    button.setMargin(new Insets(0, 0, 0, 0));
    this.add(button);
  }
}
