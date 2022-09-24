package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

public class BoardPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  static final int cols = 11;
  static final int rows = 11;

  static int originX = 200;
  static int originY = 200;
  static int xSpacing = (1000 - (originX * 2)) / cols;
  static int ySpacing = (1000 - (originY * 2)) / rows;
  static int xEndPoint = originX + xSpacing * 11;
  static int yEndPoint = originY + ySpacing * 11;

  @Override
  protected void paintComponent(Graphics g) {
    g.setColor(new Color(85, 73, 148));
    super.paintComponent(g);
    g.fillRect(0, 0, 1000, 1000);
    g.setColor(new Color(255, 204, 179));
    g.fillRect(
      originX - 10,
      originY - 10,
      xEndPoint - originX + 20,
      yEndPoint - originY + 20
    );
    g.setColor(new Color(46, 39, 82));
    g.fillRect(originX, originY, xEndPoint - originX, yEndPoint - originY);
    createGrid(g);
    updateGrid(g);
    GUI.drawText(g);
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

  public void drawImg(BufferedImage img, int x, int y, Graphics g) {
    g.drawImage(
      img,
      getXPos(x),
      getYPos(y),
      getXPos(x) + xSpacing,
      getYPos(y) + ySpacing,
      0,
      0,
      img.getWidth(),
      img.getHeight(),
      null
    );
  }

  public int getXPos(int x) {
    return originX + (xSpacing * x);
  }

  public int getYPos(int y) {
    return originY + (ySpacing * y);
  }

  public void updateGrid(Graphics g) {
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        drawImg(Img.WallSprite.image, 5, 5, g);
      }
    }
    drawImg(Img.Chap.image, 5, 5, g);
  }
  /* 
    public void parser(Tile t, Graphics g) {
        if(t.toString().equals("")){
            drawImg(Img.WallSprite.image, t.x(), t.y(), g);
        }
        else{
            drawImg(Img.FloorSprite.image, t.x(), t.y(), g);
        }
    }
    */
}
