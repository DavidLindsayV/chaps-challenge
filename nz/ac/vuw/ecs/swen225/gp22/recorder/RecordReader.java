package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class RecordReader {
    /*
     * Parses the xml file
     */
    public static Document parse(URL url) throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    /**
     * Loads a xml document
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public static void loadDoc() throws MalformedURLException, DocumentException{
        URL url;
        JFileChooser fileChooser = new JFileChooser("recorded_games/");
        int responce = fileChooser.showOpenDialog(null);
        if(responce == JFileChooser.APPROVE_OPTION){
            url = new File(fileChooser.getSelectedFile().getAbsolutePath()).toURI().toURL();
            
            //Here we have the document, currently it just prints the file to the console.
            Document doc = parse(url);

            Element root = doc.getRootElement();
            
            System.out.println(actionList(root));
        }
    }


    /* 
     * Method to read the xml file and turn it into an action list.
     */
    public static List<String> actionList(Element e) {
        List<String> moves = new ArrayList<String>();
        for(Iterator<Element> it = e.elementIterator(); it.hasNext();){
            Element tick = it.next();
            for(Iterator<Element> jt = tick.elementIterator(); jt.hasNext();){
                Element ae = jt.next();
                String action = ae.getText();
                moves.add(action);
            }
        }
        return moves;
    }

}
