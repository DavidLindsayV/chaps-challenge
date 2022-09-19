package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class RecordReader {

    
    public RecordReader(){ }

    /*
     * Parses the xml file
     */
    public static Document parse(URL url) throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

}
