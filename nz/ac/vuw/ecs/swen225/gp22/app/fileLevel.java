package nz.ac.vuw.ecs.swen225.gp22.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import javax.lang.model.element.Element;
import javax.swing.JFileChooser;
import javax.swing.text.Document;
import org.dom4j.DocumentException;

public class fileLevel {

  public static String getLevelFilename()
    throws MalformedURLException, DocumentException {
    URL url;
    JFileChooser fileChooser = new JFileChooser(
      new File(System.getProperty("user.dir")).getAbsolutePath() +
      "/nz/ac/vuw/ecs/swen225/gp22/levels/"
    );
    int responce = fileChooser.showOpenDialog(null);
    if (responce == JFileChooser.APPROVE_OPTION) {
      url =
        new File(fileChooser.getSelectedFile().getAbsolutePath())
          .toURI()
          .toURL();
      System.out.println(url);
      return url
        .toString()
        .substring(
          url.toString().indexOf("levels/") + 7,
          url.toString().length()
        );
    }
    throw new MalformedURLException();
  }

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
