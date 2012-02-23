/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import java.io.StringReader;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 *
 * @author Lin
 */
public class XML_parser_API {
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    public XML_parser_API(String xml_str){
        try{
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new StringReader(xml_str)));
            doc.getDocumentElement().normalize();
        }
        catch(Exception e){}
    }
    public String getTagValue(String sTag) {
        Element eElement = doc.getDocumentElement();
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
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
