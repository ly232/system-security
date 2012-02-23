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
    private HashMap<String,String> requestMsg;
    private int requestID;
    public clientRequest(int reqID, HashMap<String,String> reqMsg){
        this.requestID = reqID;
        this.requestMsg = reqMsg;
    }
    public String generateXMLforRequest(){
        String retXML="";
        try{

            XML_creator_API xmlapi = new XML_creator_API();
            
            switch(this.requestID){
                case Constants.LOGIN_REQUEST_ID: 
                    xmlapi.createRoot("client_login_request");
                    xmlapi.createChild("user_id", "client_login_request");
                    xmlapi.createText(this.requestMsg.get("user_id"), "user_id");
                    xmlapi.createChild("password", "client_login_request");
                    xmlapi.createText(this.requestMsg.get("password"), "password");
                    break;
                case Constants.REGIST_REQUEST_ID:
                    xmlapi.createRoot("client_regist_request");
                    xmlapi.createChild("ver_code", "client_regist_request");
                    xmlapi.createText(this.requestMsg.get("ver_code"), "ver_code");
                    xmlapi.createChild("user_id", "client_regist_request");
                    xmlapi.createText(this.requestMsg.get("user_id"), "user_id");
                    xmlapi.createChild("password", "client_regist_request");
                    xmlapi.createText(this.requestMsg.get("password"), "password");
                    xmlapi.createChild("contact_info", "client_regist_request");
                    xmlapi.createText(this.requestMsg.get("contact_info"), "contact_info");
                    xmlapi.createChild("role_id", "client_regist_request");
                    xmlapi.createText(this.requestMsg.get("role_id"), "role_id");
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

