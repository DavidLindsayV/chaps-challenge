package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Recorder {


    public static Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        root.addElement("author")
            .addAttribute("name", "Jeffe")
            .addAttribute("location", "Minis thirith")
            .addText("James Strachan");

        root.addElement("author")
            .addAttribute("name", "Bob")
            .addAttribute("location", "US")
            .addText("Bob McWhirter");

        return document;
    }

    public static void write(Document document) throws IOException {

        FileWriter out = new FileWriter("foo.xml");

        System.out.println("Writen");

        document.write(out);
        out.close();

    }

}
