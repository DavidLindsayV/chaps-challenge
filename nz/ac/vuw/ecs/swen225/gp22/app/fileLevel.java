package nz.ac.vuw.ecs.swen225.gp22.app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import javax.swing.JFileChooser;
import org.dom4j.DocumentException;

/**
 * A class for dealing with reading/writing files for level loading, saving and selecting
 * @author David Lindsay 300584648
 */
public class fileLevel {

  /**
   * A method to allow the user to select the folder containing the xml file for a level to load
   * @return shortened URL of selected level 
   * @throws MalformedURLException
   * @throws DocumentException
   */
  public static String getLevelFilename()
    throws MalformedURLException, DocumentException {
    URL url;
    JFileChooser fileChooser = new JFileChooser(
      new File(System.getProperty("user.dir")).getAbsolutePath() +
      "/nz/ac/vuw/ecs/swen225/gp22/levels/saved_games/"
    );
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int responce = fileChooser.showOpenDialog(null);
    if (responce == JFileChooser.APPROVE_OPTION) {
      url =
        new File(fileChooser.getSelectedFile().getAbsolutePath())
          .toURI()
          .toURL();
      return url
        .toString()
        ;
    }
    throw new MalformedURLException();
  }

  /**
   * Reads the name of the starting level file from initlevel.txt
   * @return Contents of initlevel.txt
   */
  public static String getStartingFileName() {
    try {
      Scanner s = new Scanner(
        new File("nz/ac/vuw/ecs/swen225/gp22/app/initLevel.txt")
      );
      return s.nextLine();
    } catch (IOException e) {
      System.out.println("initLevel.txt was not found");
    }
    return "";
  }

  /**
   * Saves the current level file in the file initlevel.txt
   * @param initLevel, the name of the level the next game will be initialised on
   */
  public static void saveStartingFileName(String initLevel) {
    try {
      Files.writeString(
        Paths.get("nz/ac/vuw/ecs/swen225/gp22/app/initLevel.txt"),
        initLevel,
        StandardOpenOption.WRITE
      );
    } catch (IOException e) {
      System.out.println("An error occurred while writing to initLevel.txt");
    }
  }
}
