/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Lin
 */
public class XML_parser_API {
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    
    public XML_parser_API(String xml_str) throws ParserConfigurationException, SAXException, IOException{
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new StringReader(xml_str)));
            doc.getDocumentElement().normalize();

    }
    public String getTagValue(String sTag) {
        Element eElement = doc.getDocumentElement();
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue!=null)
            return nValue.getNodeValue();
        else
            return null;
    }
    public String getRootTagName(){
        return doc.getDocumentElement().getNodeName();
    }
    public HashMap<String,String> XML2Table(){ //assume the XML tree is of height 1...otherwise this api doesnt work because we dont reach the leaf and there's no text to extract for hashmap's value
        HashMap<String,String> hm = new HashMap<String,String>();
        NodeList nl = doc.getDocumentElement().getChildNodes();
        for (int i=0;i<nl.getLength();i++){
            hm.put(nl.item(i).getNodeName(), nl.item(i).getTextContent());
        }
        return hm;
    }
}
