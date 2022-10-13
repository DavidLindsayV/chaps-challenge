package nz.ac.vuw.ecs.swen225.gp22.renderer.Sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * A class that allows access to the different sprites.
 * @author Adam Goodyear 300575240
 */

public enum Img {
    WallSprite,
    FloorSprite,
    BlueDoor,
    GreenDoor,
    PurpleDoor,
    RedDoor,
    YellowDoor,
    BlueKey,
    GreenKey,
    PurpleKey,
    RedKey,
    YellowKey,
    BlueKeyT,
    GreenKeyT,
    PurpleKeyT,
    RedKeyT,
    YellowKeyT,
    TreasureT,
    Exit,
    ExitLock,
    Treasure,
    InfoField,
    Enemy,
    Empty,
    Title,
    ChapL,
    ChapR;
    Img() {
        image = loadImage(this.name());
    }

    /**
    * Loads the image by finding it inside the Sprites folder and returning it. 
    * @return Buffered Image based on the name inputted. 
    * @param name takes the name of the image.
    */
    static private BufferedImage loadImage(String name) {
        URL imagePath = Img.class.getResource(name + ".png");
        try {
            return ImageIO.read(imagePath);
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}