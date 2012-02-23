/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import java.util.*;
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

            XML_API xmlapi = new XML_API();
            
            switch(this.requestID){
                case Constants.LOGIN_REQUEST_ID: 
                    xmlapi.createRoot("client_login_request");
                    xmlapi.createChild("user_id", "client_login_request");
                    xmlapi.createText(this.requestMsg.get(0), "user_id");
                    xmlapi.createChild("password", "client_login_request");
                    xmlapi.createText(this.requestMsg.get(1), "password");

                    break;
                case Constants.REGIST_REQUEST_ID:

                    break;
                case Constants.REGIST_INFO_ID:

                    break;
                default:

                    break;
            }
            
            retXML = xmlapi.getXMLstring();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return retXML;
        }
    }
}

