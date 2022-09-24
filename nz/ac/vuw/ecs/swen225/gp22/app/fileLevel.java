package nz.ac.vuw.ecs.swen225.gp22.app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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
}
