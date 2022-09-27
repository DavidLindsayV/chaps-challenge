package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import nz.ac.vuw.ecs.swen225.gp22.app.moveType;

public class RecordReader {
    
    /*
     * Parses the xml file
     */
    private static Document parse(URL url) throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    /**
     * Loads a recorded game xml document
     * 
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
            try {
                List<moveType> actionList = actionList(doc);
                System.out.println(actionList.size());
            } catch (XmlFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /* 
     * Method to read the xml file and turn it into an action list.
     */
    private static List<moveType> actionList(Document doc) throws XmlFormatException {
        Element e = doc.getRootElement();
        List<moveType> moves = new ArrayList<moveType>();
        for(Iterator<Element> it = e.elementIterator(); it.hasNext();){
            Element tick = it.next();
            if(!(tick.node(1) instanceof Element))  {
                throw new XmlFormatException("tick.node(1) is not an element!");
            }
            Element moveEle = (Element) tick.node(1);
            if(!moveEle.getName().equals("move")) {
                throw new XmlFormatException("The element tick.node(1) is not a move!");
            }
            String moveStr = moveEle.getText();
            moveType move = moveType.valueOf(moveStr);
            moves.add(move);
        }
        return Collections.unmodifiableList(moves);
    }

    /* 
     * Custom exception for checking that the xml recorded games are correctly formatted
     */
    private static class XmlFormatException extends Exception { 
        XmlFormatException(String errorMessage) {
            super("Incorrect XML format: "+errorMessage);
        }
    }
}
