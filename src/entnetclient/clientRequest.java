/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

//import XML.Constants;
import java.util.*;

import Constants.*;
import XML.*;

/**
 *
 * @author Lin
 */
public class clientRequest {
    private HashMap<String,String> requestMsg;
    private String requestID;
    private String userID;
    public clientRequest(String reqID, String uID, HashMap<String,String> reqMsg){
        this.requestID = reqID;
        this.requestMsg = reqMsg;
        this.userID = uID;
    }
    public String generateXMLforRequest(){
        String retXML="";
        try{

            //XML_creator_API xmlapi = new XML_creator_API();
            if (this.requestID.equals(Constants.LOGIN_REQUEST_ID)){
                retXML = clientRequestLogin();
            }
            else if (this.requestID.equals(Constants.REGIST_REQUEST_ID)){
                retXML = clientRequestRegist();
            }


        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return retXML;
        }
    }

    
    
    private String clientRequestLogin() {
        String myQuery = "SELECT * FROM user WHERE user_id='"
                +requestMsg.get("user_id")+"' AND user_pwd='"
                +requestMsg.get("password") +"'";
        
        XMLRequest xmlapi = new XMLRequest(
                Constants.LOGIN_REQUEST_ID,
                this.userID,
                "", //region id
                "", //session id...not for 
                myQuery, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
                );
                
        
        
               return xmlapi.generateXMLRequest();
     
    }

    private String clientRequestRegist() {

                    String myQuery = "INSERT INTO user VALUES ('" + requestMsg.get("user_id") 
                    + "', '" + requestMsg.get("password") 
                    + "', '" + requestMsg.get("person_name")
                    + "', '" + requestMsg.get("contact_info")
                    + "', '" + requestMsg.get("role_id") + "')";
        
                    
                    
        XMLRequest xmlapi = new XMLRequest(
                Constants.REGIST_REQUEST_ID,
                this.userID,
                "", //region id
                "", //session id...not for 
                myQuery, //request detail...SQL statement to be excuted by server
                "UPDATE" //action id...can either be SELECT or UPDATE...see Constants package
                );
                
               return xmlapi.generateXMLRequest();
    }
}

