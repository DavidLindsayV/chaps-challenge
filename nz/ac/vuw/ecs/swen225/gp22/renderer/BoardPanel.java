package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class BoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    static final int cols = 11;
    static final int rows = 11;

    static int originX = 200;
    static int originY = 200;
    int xSpacing = (1000 - (originX * 2)) / cols;
    int ySpacing = (1000 - (originY * 2)) / rows;
    int xEndPoint = originX + xSpacing * 11;
    int yEndPoint = originY + ySpacing * 11;

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(85, 73, 148));
        super.paintComponent(g);
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(new Color(46, 39, 82));
        g.fillRect(originX, originY, xEndPoint - originX, yEndPoint - originY);
        createGrid(g);
    }

    private void createGrid(Graphics g) {
        g.setColor(new Color(255, 204, 179));
        int tempOriginX = originX;
        for (int i = 0; i <= cols; i++) {
            g.drawLine(originX, originY, originX, yEndPoint);
            originX = originX + (xSpacing);
        }
        originX = tempOriginX;

        int tempOriginY = originY;
        for (int i = 0; i <= rows; i++) {
            g.drawLine(originX, originY, xEndPoint, originY);
            originY = originY + (ySpacing);
        }
        originY = tempOriginY;
    }

    public int getXPos(int x) {
        return originX + (xSpacing * x);
    }

    public int getYPos(int y) {
        return originY + (ySpacing * y);
    }
}
