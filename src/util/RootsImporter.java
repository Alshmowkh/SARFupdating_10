package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RootsImporter {

    static String source;
    static String distenation;

    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document doc;

    public RootsImporter() throws ParserConfigurationException {
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
//        doc = db.newDocument();
    }

    List<String> importRoots() throws SAXException, IOException {
        List<String> rawroots = new ArrayList();
        Document docimport = db.parse(new File(source));
        NodeList rootsNodes = docimport.getElementsByTagName("root");
        for (int i = 0; i < rootsNodes.getLength(); i++) {
            String rot = rootsNodes.item(i).getAttributes().getNamedItem("value").getNodeValue().toLowerCase().trim();
            rawroots.add(rot);
        }
        return rawroots;
    }

    Boolean exportRoots(List<String> rawroots) throws SAXException, IOException, TransformerException {
        List pureList = new ArrayList();
        Document docexport = db.newDocument();

        Element roots = docexport.createElement("roots");
        Element root;// = docexport.createElement("root");
        int id = 0;
        for (int i = 0; i < rawroots.size(); i++) {
            String rootText = rawroots.get(i);
            if (!pureList.contains(rootText)) {
                pureList.add(rootText);
                root = docexport.createElement("root");
                root.setAttribute("value", rootText);
                root.setAttribute("id", id++ + "");
                roots.appendChild(root);

                pl(i + ": " + rootText);
            }
        }

        docexport.appendChild(roots);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();

        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty(OutputKeys.VERSION, "1.0");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        docexport.setXmlStandalone(true);
        DOMSource dom = new DOMSource(docexport);
        File out = new File(distenation);
        StreamResult sr = new StreamResult(out);
        t.transform(dom, sr);

        if (out.exists()) {
            System.out.println("The db has been done created in directory : " + out.getAbsolutePath());
        } else {
            System.out.println("There is error at creating db.");
        }
        return true;
    }

    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {

        source = "./Alshmowkh_db/TrilateralRoots.xml";
        distenation = "./Alshmowkh_db/TrilateralRootsNoDuplicate.xml";
        RootsImporter cls = new RootsImporter();
        pl(cls.exportRoots((cls.importRoots())));
    }

    static void pl(Object o) {
        System.out.println(o);
    }

}
