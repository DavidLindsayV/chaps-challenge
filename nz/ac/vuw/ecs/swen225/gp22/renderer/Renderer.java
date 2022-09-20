package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import nz.ac.vuw.ecs.swen225.gp22.app.pingTimer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer extends JFrame {
    private int width;
    private int height;

    public Renderer(int w, int h) {
        width = w;
        height = h;
    }

    public void setUpGUI() {
        setSize(width, height);
        setTitle("Test Title");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoardPanel panel = new BoardPanel();
        add(panel);
        setVisible(true);
    }

    public void drawGrid() {

    }
}