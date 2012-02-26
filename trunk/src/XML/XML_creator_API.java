/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.io.*;
import javax.xml.soap.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Lin
 */
public class XML_creator_API {
    //class variables for creating XML doc:
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private Element rootElement;

    public XML_creator_API(){
        try{
            this.docFactory = DocumentBuilderFactory.newInstance();
            this.docBuilder = docFactory.newDocumentBuilder();
            this.doc = docBuilder.newDocument();
            
        }catch(Exception e){};
    }
    
    public void createRoot(String rootTagName){
        this.rootElement = doc.createElement(rootTagName);
        doc.appendChild(rootElement);
    }
    
    //creates a tag child node for every node named "parentTagName"
    public void createChild(String childTagName, String parentTagName){
        Element newElement = doc.createElement(childTagName);
        NodeList nodelist= doc.getElementsByTagName(parentTagName);
        
        for (int i=0;i<nodelist.getLength();i++){
            nodelist.item(i).appendChild(newElement);
        }
    }
    
    //create a text child node for every node named "parentTagName"
    public void createText(String childText, String parentTagName){
        NodeList nodelist= doc.getElementsByTagName(parentTagName);
        for (int i=0;i<nodelist.getLength();i++){
            nodelist.item(i).appendChild(doc.createTextNode(childText));
        }
    }
    
    public String getXMLstring(){
        String retXML = "";
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            OutputStream baos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(baos);
            transformer.transform(source, result);
            retXML = baos.toString();
        }catch(Exception e){}
        finally{
            return retXML;
        }
    }
    
    public String getXMLsubtree(String subtreeRootName){
        String retXMLsubtreeString = "";
        
        NodeList nodelist= doc.getElementsByTagName(subtreeRootName);        
        
        Node subtreeRoot = (Node) nodelist.item(0);
        try{
            DocumentBuilderFactory myDocFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder myDocBuilder = docFactory.newDocumentBuilder();
            Document myDoc = docBuilder.newDocument();
        }catch(Exception e){}
        
        doc.appendChild(subtreeRoot);
        
        
        
        //TODO: complete 
        
        
        
        return retXMLsubtreeString;
    }
    
}
