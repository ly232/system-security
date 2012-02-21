/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;


//import java.io.File;
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
/**
 *
 * @author Lin
 */
public class clientRequest {
    private ArrayList<String> requestMsg;
    private int requestID;
    public clientRequest(int reqID, ArrayList<String> reqMsg){
        this.requestID = reqID;
        this.requestMsg = reqMsg;
    }
    public String generateXMLforRequest(){
        String retXML="";
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // root elements
	    Document doc = docBuilder.newDocument();
            Element rootElement;
            switch(this.requestID){
                case Constants.LOGIN_REQUEST_ID: 
                    rootElement = doc.createElement("client_login_request");
                    Element userID = doc.createElement("user_id");
                    userID.appendChild(doc.createTextNode(this.requestMsg.get(0)));
                    Element userPwd = doc.createElement("password");
                    userPwd.appendChild(doc.createTextNode(this.requestMsg.get(1)));
                    rootElement.appendChild(userID);
                    rootElement.appendChild(userPwd);
                    break;
                case Constants.REGIST_REQUEST_ID:
                    rootElement = doc.createElement("client_regist_request");
                    break;
                case Constants.REGIST_INFO_ID:
                    rootElement = doc.createElement("client_regist_info");
                    break;
                default:
                    rootElement = doc.createElement("");
                    break;
            }
            doc.appendChild(rootElement);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            OutputStream baos = new ByteArrayOutputStream();
            
            StreamResult result = new StreamResult(baos);
            transformer.transform(source, result);
            retXML = baos.toString();
            //System.out.println("myXML="+myXML);
            //System.out.println("finished with generateXML method");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return retXML;
        }
    }
}

