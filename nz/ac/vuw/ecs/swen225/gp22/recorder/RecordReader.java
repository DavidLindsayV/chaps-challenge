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

public class RecordReader {

    /**
     * Loads a recorded game xml document
     * 
     * @throws MalformedURLException
     * @throws DocumentException
     * 
     * @return List<E> Where E is an enum
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
     * @throws DocumentException
     * @throws MalformedURLException
     */
    public static <E extends Enum<E>> void loadPartial(Class<E> clazz) throws MalformedURLException, DocumentException {
        
        Document doc = DocumentHelper.createDocument();
        List<E> actionList = loadDoc(clazz);

        //Set writer first to change the writer
        Recorder.setWriter(new RecordWriter(doc));
        for(E action : actionList){
            Recorder.tick(action);
        }
        Recorder.setDocument(doc);
    }
    
    /**
     * Parses the xml file
     */
    private static Document parse(URL url) throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    /**
     * Method to read the xml file and turn it into an action list.
     */
    private static <E extends Enum<E>> List<E> actionList(Class<E> clazz, Document doc) throws XmlFormatException {
        Element e = doc.getRootElement();
        List<E> moves = new ArrayList<E>();
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
