package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * This is the class we will be using to read the saved xml files
 */
public class RecordReader {

    /**
     * Loads a recorded game xml document.
     * 
     * @param <E> - The generic enum.
     * @param clazz - The class access for the enum.
     * @return - A list of enum of moves.
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public static <E extends Enum<E>> List<E> loadDoc(Class<E> clazz) throws MalformedURLException, DocumentException{
        URL url;
        JFileChooser fileChooser = new JFileChooser("recorded_games/");
        int responce = fileChooser.showOpenDialog(null);
        if(responce == JFileChooser.APPROVE_OPTION){
            url = new File(fileChooser.getSelectedFile().getAbsolutePath()).toURI().toURL();
            Document doc = parse(url);
            try {
                return actionList(clazz,doc);
            } catch (XmlFormatException e) { e.printStackTrace(); }
        }
        JOptionPane.showMessageDialog(null, "No file chosen!", null, JOptionPane.INFORMATION_MESSAGE);
        return List.of();
    }


    /**
     * Load a partially completed game for saving again.
     * 
     * @param <E> - The generic enum.
     * @param clazz - The class access for the enum.
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public static <E extends Enum<E>> void loadPartial(Class<E> clazz) throws MalformedURLException, DocumentException {
        
        Document doc = DocumentHelper.createDocument();
        List<E> actionList = loadDoc(clazz);

        //Set writer first to change the writer
        Recorder.setWriter(new RecordWriter(doc, Recorder.getLevel()));
        for(E action : actionList){
            Recorder.tick(action);
        }
        Recorder.setDocument(doc);
    }
    

    /**
     * Parses the xml file.
     * 
     * @param url - The url of the document we are reading.
     * @return - A dom4j document from the file.
     * @throws DocumentException
     */
    private static Document parse(URL url) throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }


    /**
     * Method to read the xml file and turn it into an action list.
     * 
     * @param <E> - The generic enum.
     * @param clazz - The class access for the enum.
     * @param doc - A dom4j document we are going to turn into a list.
     * @return - A list of enum of moves.
     * @throws XmlFormatException
     */
    private static <E extends Enum<E>> List<E> actionList(Class<E> clazz, Document doc) throws XmlFormatException {
        Element e = doc.getRootElement();
        List<E> moves = new ArrayList<E>();
        for(Iterator<Element> it = e.elementIterator(); it.hasNext();){
            Element tick = it.next();

            if(tick.getName().equals("level")){
                Recorder.setLevel(tick.getText());
                tick = it.next();
            }

            if(!(tick.node(1) instanceof Element))  {
                throw new XmlFormatException("tick.node(1) is not an element!");
            }
            Element moveEle = (Element) tick.node(1);
            if(!moveEle.getName().equals("move")) {
                throw new XmlFormatException("The element tick.node(1) is not a move!");
            }
            String moveStr = moveEle.getText();
            E move = E.valueOf(clazz, moveStr);
            moves.add(move);
        }

        return Collections.unmodifiableList(moves);
    }

    /**
     * Custom exception for checking that the xml recorded games are correctly formatted
     */
    private static class XmlFormatException extends Exception { 
        XmlFormatException(String errorMessage) {
            super("Incorrect XML format: "+errorMessage);
        }
    }
}
