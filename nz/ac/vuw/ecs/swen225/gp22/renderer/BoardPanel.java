package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.app.Main;
import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;
import nz.ac.vuw.ecs.swen225.gp22.domain.AuthenticationColour;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.Player;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.WallTile;
import nz.ac.vuw.ecs.swen225.gp22.recorder.ReplayGui;
import nz.ac.vuw.ecs.swen225.gp22.recorder.ReplayListener;
import nz.ac.vuw.ecs.swen225.gp22.domain.Player;
import nz.ac.vuw.ecs.swen225.gp22.domain.Enemy;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites.Img;

/**
 * class which renders the board and
 * supplies functions to locate positions on the board.
 * @author Adam Goodyear 300575240
 */

public class BoardPanel extends JPanel {

  //sets up known variables
  private static final long serialVersionUID = 1L;
  static final int cols = 11;
  static final int rows = 11;
  public static boolean chapDirection = true;

  //funny maths stuff
  static int originX = 200;
  static int originY = 200;
  static int xSpacing = (1000 - (originX * 2)) / cols;
  static int ySpacing = (1000 - (originY * 2)) / rows;
  static int xEndPoint = originX + xSpacing * 11;
  static int yEndPoint = originY + ySpacing * 11;

  /**
  * paint component. beins drawing the simple elements
  * @param g takes a graphics component
  */
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

    if (GUI.replayGui() != null) {
      updateGrid(ReplayListener.currentGame, g);
      ReplayGui.drawText(g);
    } else if (GUI.instance != null) {
      updateGrid(UserListener.currentGame, g);
      GUI.drawText(g);
    }
  }

  /**
  * creates a base grid to base measurements off.
  * @param g takes a graphics component
  */
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

  /**
  * draws an image using the Graphics draw image function built to fit into the grid.
  * @param img the buffered image that will be drawn
  * @param x X position of image
  * @param y Y position of image
  * @param g graphics class used
  */
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

  /**
  * Figures out the new X position of a value when placed into the grid
  * @param x
  * @return Returns the new X position
  */
  public int getXPos(int x) {
    return originX + (xSpacing * x);
  }

  /**
  * Figures out the new Y position of a value when placed into the grid
  * @param y
  * @return Returns the new Y position
  */
  public int getYPos(int y) {
    return originY + (ySpacing * y);
  }

  /**
  * Updates the Grid
  * @param d The domain that will be passed to tileParser
  * @param g Graphics class used
  */
  public void updateGrid(Domain d, Graphics g) {
    TileParser(d, g);
    if(chapDirection){
      drawImg(Img.ChapL.image, 5, 5, g);
    } else {
      drawImg(Img.ChapR.image, 5, 5, g);
    }
  }

  /**
  * loops through all tiles around the player to draw them on the screen then draws enemies if needed.
  * @param d The domain for getting player position and tiles
  * @param g Graphics class used
  */
  public void TileParser(Domain d, Graphics g) {
    Tile[][] t = d.getGraphicalState();
    Point p = d.getPlayerGraphicalPosition();

    for (int y = p.row() - 5; y < p.row() + 6; y++) {
      for (int x = p.col() - 5; x < p.col() + 6; x++) {
        int newX = x - (p.col() - 5);
        int newY = y - (p.row() - 5);

        // if out of bounds then draw floorsprite and continue
        if (y >= t.length || y < 0) {
          drawImg(Img.FloorSprite.image, newX, newY, g);
          continue;
        }
        if (x >= t[0].length || x < 0) {
          drawImg(Img.FloorSprite.image, newX, newY, g);
          continue;
        }

        if (t[y][x].name().equals("wall")) {
          drawImg(Img.WallSprite.image, newX, newY, g);
        } else if (t[y][x].name().equals("treasure")) {
          drawImg(Img.Treasure.image, newX, newY, g);
        } else if (t[y][x].name().equals("exitLock")) {
          drawImg(Img.ExitLock.image, newX, newY, g);
        } else if (t[y][x].name().equals("exit")) {
          drawImg(Img.Exit.image, newX, newY, g);
        } else if (t[y][x].name().equals("info")) {
          drawImg(Img.InfoField.image, newX, newY, g);
        } else if (t[y][x].name().equals("key")) {
          if (t[y][x].colour().equals("PINK")) {
            drawImg(Img.RedKey.image, newX, newY, g);
          } else if (t[y][x].colour().equals("GREEN")) {
            drawImg(Img.GreenKey.image, newX, newY, g);
          } else if (t[y][x].colour().equals("PURPLE")) {
            drawImg(Img.PurpleKey.image, newX, newY, g);
          } else if (t[y][x].colour().equals("BLUE")) {
            drawImg(Img.BlueKey.image, newX, newY, g);
          } else if (t[y][x].colour().equals("YELLOW")) {
            drawImg(Img.YellowKey.image, newX, newY, g);
          } else {
            drawImg(Img.Empty.image, newX, newY, g);
          }
        } else if (t[y][x].name().equals("door")) {
          if (t[y][x].colour().equals("PINK")) {
            drawImg(Img.RedDoor.image, newX, newY, g);
          } else if (t[y][x].colour().equals("GREEN")) {
            drawImg(Img.GreenDoor.image, newX, newY, g);
          } else if (t[y][x].colour().equals("Purple")) {
            drawImg(Img.PurpleDoor.image, newX, newY, g);
          } else if (t[y][x].colour().equals("BLUE")) {
            drawImg(Img.BlueDoor.image, newX, newY, g);
          } else if (t[y][x].colour().equals("YELLOW")) {
            drawImg(Img.YellowDoor.image, newX, newY, g);
          } else {
            drawImg(Img.Empty.image, newX, newY, g);
          }
        } else if (t[y][x].name().equals("empty")) {
          drawImg(Img.FloorSprite.image, newX, newY, g);
        } else {
          drawImg(Img.Empty.image, newX, newY, g);
        }
      }
    }

    d.getEnemies().stream()
      .filter(e -> e.getPosition().col() - (p.col() - 6) > -1 && e.getPosition().col() - (p.col() - 6) < 11 && e.getPosition().row() - (p.row() - 6) > -1 && e.getPosition().row() - (p.row() - 6) < 11)
      .forEach(e -> drawImg(Img.Enemy.image, e.getPosition().col() - (p.col() - 6), e.getPosition().row() - (p.row() - 6),g));
  }
}
