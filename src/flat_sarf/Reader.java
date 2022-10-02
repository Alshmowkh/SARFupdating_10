package flat_sarf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Reader {

    public String madaFilePath;
    DocumentBuilderFactory dbf;
    public DocumentBuilder db;
    Document doc;
    public String query;
    public XPath xpath;

    public Reader() {

    }

    public void setSarfPath(String path) {
        madaFilePath = path;
        query = null;
    }

    public Reader(String path) throws ParserConfigurationException, SAXException, IOException {
        madaFilePath = path;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.parse(new File(madaFilePath));
        xpath = XPathFactory.newInstance().newXPath();
    }

    public Node root() {

        return doc.getDocumentElement();
    }

    public NodeList getSentences(Node index) throws XPathExpressionException {
        String specificDoc = index.getAttributes().getNamedItem("id").getNodeValue();
        query = "/" + root().getNodeName() + "/" + "out_doc[@ id='" + specificDoc + "']/out_seg";
        NodeList sentences = (NodeList) xpath.evaluate(query, root(), XPathConstants.NODESET);
        return sentences;
    }

    NodeList getSentences(String docID) throws XPathExpressionException {

        query = "/" + root().getNodeName() + "/" + "out_doc[@ id='" + docID + "']/out_seg";
        NodeList sentences = (NodeList) xpath.evaluate(query, root(), XPathConstants.NODESET);
        return sentences;
    }

    public NodeList getWordsOfSentence(Node docID, Node sentID) throws XPathExpressionException {
        String specificDoc = docID.getAttributes().getNamedItem("id").getNodeValue();
        String specificSent = sentID.getAttributes().getNamedItem("id").getNodeValue();
        query = "/" + root().getNodeName() + "/" + "out_doc[@ id='" + specificDoc + "']/out_seg[@ id='" + specificSent + "']/word_info/word";
        NodeList words = (NodeList) xpath.evaluate(query, root(), XPathConstants.NODESET);
        return words;
    }

    NodeList getDocuments() {

        return null;
    }

    List<NodeList> getAllRoots() throws XPathExpressionException {
        List<NodeList> allWords = new ArrayList<>();
        NodeList docs = getDocuments();
        for (int i = 0; i < docs.getLength(); i++) {
            NodeList sentences = getSentences(docs.item(i));
            for (int j = 0; j < sentences.getLength(); j++) {
                NodeList words = getWordsOfSentence(docs.item(i), sentences.item(j));
                allWords.add(words);
            }
        }
        return allWords;
    }

    public List<Node> getNodeParents(Node word) {
        List<Node> Paths = new ArrayList<>();
        if (isWord(word)) {
            Paths.add(word.getParentNode());
            Paths.add(word.getParentNode().getParentNode());
            Paths.add(word.getParentNode().getParentNode().getParentNode());
        } else if (isSentence(word)) {
            Paths.add(word.getParentNode());
        } else if (isDocument(word)) {
            Paths.add(word.getParentNode());
        }

        return Paths;
    }

    NamedNodeMap getMorphemes(Node word) throws XPathExpressionException {
        String wordID = word.getAttributes().getNamedItem("id").getNodeValue();
        String wordDoc = getNodeParents(word).get(2).getNodeName();
        String wordDocID = getNodeParents(word).get(2).getAttributes().getNamedItem("id").getNodeValue();
        String wordSeg = getNodeParents(word).get(1).getNodeName();
        String wordSegID = getNodeParents(word).get(1).getAttributes().getNamedItem("id").getNodeValue();
        String wordInfo = getNodeParents(word).get(0).getNodeName();
        String svmAnalysis = "svm_prediction";
        String fullquery = "/" + root().getNodeName() + "/" + wordDoc + "[@ id='" + wordDocID + "']/" + wordSeg + "[@ id='" + wordSegID + "']/" + wordInfo + "/" + word.getNodeName() + "[@ id='" + wordID + "']/" + svmAnalysis + "/morph_feature_set";

        NodeList morph = (NodeList) xpath.evaluate(fullquery, root(), XPathConstants.NODESET);
        if (morph.getLength() == 0) {
            svmAnalysis = "analysis";
            morph = (NodeList) xpath.evaluate(fullquery, root(), XPathConstants.NODESET);
        }
        return morph.item(0).getAttributes();
    }

    public Map getMorphemesMap(Node word) throws XPathExpressionException {
        String wordID = word.getAttributes().getNamedItem("id").getNodeValue();
        String wordDoc = getNodeParents(word).get(2).getNodeName();
        String wordDocID = getNodeParents(word).get(2).getAttributes().getNamedItem("id").getNodeValue();
        String wordSeg = getNodeParents(word).get(1).getNodeName();
        String wordSegID = getNodeParents(word).get(1).getAttributes().getNamedItem("id").getNodeValue();
        String wordInfo = getNodeParents(word).get(0).getNodeName();
        String svmAnalysis = "svm_prediction";
        String fullquery = "/" + root().getNodeName() + "/" + wordDoc + "[@ id='" + wordDocID + "']/" + wordSeg + "[@ id='" + wordSegID + "']/" + wordInfo + "/" + word.getNodeName() + "[@ id='" + wordID + "']/" + svmAnalysis + "/morph_feature_set";

        NodeList morph = (NodeList) xpath.evaluate(fullquery, root(), XPathConstants.NODESET);
        if (morph.getLength() == 0) {
            svmAnalysis = "analysis";
            morph = (NodeList) xpath.evaluate(fullquery, root(), XPathConstants.NODESET);
        }
        Map map = new HashMap<>();
        NamedNodeMap feats = morph.item(0).getAttributes();
        for (int i = 0; i < feats.getLength(); i++) {
            map.put(feats.item(i).getNodeName(), feats.item(i).getNodeValue());
        }
        return map;
    }

    String getMorpheme(Node word, String tag) throws XPathExpressionException {
        NamedNodeMap morphemes = getMorphemes(word);
        for (int i = 0; i < morphemes.getLength(); i++) {
            if (morphemes.item(i).getNodeName().equals(tag)) {
                return morphemes.item(i).getNodeValue();
            }
        }
        return "";
    }

    String getSentenceOfWord(Node word2) {
        return getNodeParents(word2).get(1).getAttributes().getNamedItem("id").getNodeValue();
    }

    public String getDocumentOfSentence(Node sentence) {
        return getNodeParents(sentence).get(0).getAttributes().getNamedItem("id").getNodeValue();
    }

    public NodeList xpathQuery(String fullquery) throws XPathExpressionException {
        return (NodeList) xpath.evaluate(fullquery, root(), XPathConstants.NODESET);
    }

    NamedNodeMap getNodeAttributes(Node node) throws XPathExpressionException {
        NodeList nodes;

        if (isWord(node)) {
            query = "/" + root().getNodeName() + "/" + "out_doc[@ id='" + getNodeParents(node).get(2).getAttributes().getNamedItem("id").getNodeValue() + "']/out_seg[@ id='" + getSentenceOfWord(node) + "']/word_info/word[@ id='" + node.getAttributes().getNamedItem("id").getNodeValue() + "']";

        } else if (isSentence(node)) {
            query = "/" + root().getNodeName() + "/" + "out_doc[@ id='" + getDocumentOfSentence(node) + "']/out_seg[@ id='" + node.getAttributes().getNamedItem("id").getNodeValue() + "']";

        } else if (isDocument(node)) {
            query = "/" + root().getNodeName() + "/" + "out_doc[@ id='" + node.getAttributes().getNamedItem("id").getNodeValue() + "']";
        } else {
            query = "//" + node.getNodeName();
        }

        nodes = xpathQuery(query);
        return nodes.item(0).getAttributes();
    }

    Boolean isSentence(Node node) {
        return node.getNodeName().equals("out_seg");
    }

    Boolean isWord(Node node) {
        return node.getNodeName().equals("word");
    }

    Boolean isDocument(Node node) {
        return node.getNodeName().equals("out_doc");
    }

    public NodeList getD3TokensScheme(Node word) throws XPathExpressionException {
        String wordID = word.getAttributes().getNamedItem("id").getNodeValue();
        String wordDoc = getNodeParents(word).get(2).getNodeName();
        String wordDocID = getNodeParents(word).get(2).getAttributes().getNamedItem("id").getNodeValue();
        String wordSeg = getNodeParents(word).get(1).getNodeName();
        String wordSegID = getNodeParents(word).get(1).getAttributes().getNamedItem("id").getNodeValue();
        String wordInfo = getNodeParents(word).get(0).getNodeName();
        String fullquery = "/" + root().getNodeName() + "/" + wordDoc + "[@ id='" + wordDocID + "']/" + wordSeg + "[@ id='" + wordSegID + "']/" + wordInfo + "/" + word.getNodeName() + "[@ id='" + wordID + "']/tokenized [@ scheme='MyD3']/tok";

        NodeList tokens = (NodeList) xpath.evaluate(fullquery, root(), XPathConstants.NODESET);

//        System.out.println(word.getAttributes().getNamedItem("word").getNodeValue());
        return tokens;
    }

    public String getWordStem(Node word) throws XPathExpressionException {
        String wordID = word.getAttributes().getNamedItem("id").getNodeValue();
        String wordDoc = getNodeParents(word).get(2).getNodeName();
        String wordDocID = getNodeParents(word).get(2).getAttributes().getNamedItem("id").getNodeValue();
        String wordSeg = getNodeParents(word).get(1).getNodeName();
        String wordSegID = getNodeParents(word).get(1).getAttributes().getNamedItem("id").getNodeValue();
        String wordInfo = getNodeParents(word).get(0).getNodeName();
        String fullquery = "/" + root().getNodeName() + "/" + wordDoc + "[@ id='" + wordDocID + "']/" + wordSeg + "[@ id='" + wordSegID + "']/" + wordInfo + "/" + word.getNodeName() + "[@ id='" + wordID + "']/analysis/morph_feature_set";

        NodeList stem = (NodeList) xpath.evaluate(fullquery, root(), XPathConstants.NODESET);

        String s = word.getAttributes().getNamedItem("word").getNodeValue();
        try {
            if (stem.item(0).getNodeType() == Node.ELEMENT_NODE) {
                s = stem.item(0).getAttributes().getNamedItem("stem").getNodeValue();
            }
        } catch (NullPointerException p) {

        }
        return s;
    }

    void insertElement(Node dest, Element source) throws TransformerException {

//        Node node=this.getSentenceOfWord(dest)
        dest.appendChild(source);

        //------------------------------------------------------
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();

        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource dom = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File("Shom_lib\\xmlFileOut.xml"));
        t.transform(dom, sr);
    }

    public Node getWord(Node docNode, Node sentNode, String wordID) throws XPathExpressionException {
        query = "//out_doc[@ id='" + docNode.getAttributes().getNamedItem("id").getNodeValue() + "']/out_seg[@ id='" + sentNode.getAttributes().getNamedItem("id").getNodeValue() + "']/word_info/word[@ id='" + wordID + "']";
        return this.xpathQuery(query).item(0);
    }

    public Node getWord(String docNode, String sentNode, String wordID) throws XPathExpressionException {
        query = "//out_doc[@ id='" + docNode + "']/out_seg[@ id='" + sentNode + "']/word_info/word[@ id='" + wordID + "']";
        return this.xpathQuery(query).item(0);
    }

    void getAllTerms() {
        pl("start...");

        NodeList terms = doc.getElementsByTagName("term");
        pl(terms.getLength());
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String pth = "./ALshmowkh_db/test/test55.xml";
        Reader rdr = new Reader(pth);
        rdr.getAllTerms();

    }

    static void pl(Object o) {
        System.out.println(o);
    }
}
