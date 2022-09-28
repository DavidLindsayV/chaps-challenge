package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.WallTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

public class BoardPanel extends JPanel {

    public Domain domain;
    BoardPanel(Domain domain){this.domain = domain;}

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
    updateGrid(domain, g);
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

    public void updateGrid(Domain d, Graphics g) {
        TileParser(d, g);
        drawImg(Img.Chap.image, 5, 5, g);
    }
    
    public void TileParser(Domain d, Graphics g) {
        Tile[][] t = d.getInnerState();
        Point p = d.getPlayerPosition();
        for(int x = p.col() - 5; x < p.col()+5; x++){
            for(int y = p.row() - 5; y < p.row()+5; y++){
                if(t[x][y].name().equals("wall")){
                    drawImg(Img.WallSprite.image, x, y, g);
                }
                else if(t[x][y].name().equals("treasure")){
                    drawImg(Img.WallSprite.image, x, y, g);
                }
                else if(t[x][y].name().equals("exit_lock")){
                    drawImg(Img.ExitLock.image, x, y, g);
                }
                else if(t[x][y].name().equals("exit")){
                    drawImg(Img.Exit.image, x, y, g);
                }
                else if(t[x][y].name().equals("info")){
                    drawImg(Img.InfoField.image, x, y, g);
                }
                else if(t[x][y].name().equals("key")){
                    if(t[x][y].colour().equals("Red")){
                        drawImg(Img.RedKey.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Green")){
                        drawImg(Img.GreenKey.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Purple")){
                        drawImg(Img.PurpleKey.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Blue")){
                        drawImg(Img.BlueKey.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Yellow")){
                        drawImg(Img.YellowKey.image, x, y, g);
                    }
                }
                else if(t[x][y].name().equals("locked_door")){
                    if(t[x][y].colour().equals("Red")){
                        drawImg(Img.RedDoor.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Green")){
                        drawImg(Img.GreenDoor.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Purple")){
                        drawImg(Img.PurpleDoor.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Blue")){
                        drawImg(Img.BlueDoor.image, x, y, g);
                    }
                    else if(t[x][y].colour().equals("Yellow")){
                        drawImg(Img.YellowDoor.image, x, y, g);
                    }
                }
                else if(t[x][y].name().equals("empty")){
                    drawImg(Img.FloorSprite.image, x, y, g);
                }
                else{
                    drawImg(Img.Empty.image, x, y, g);
                }
            }
        }
    }
    
}
