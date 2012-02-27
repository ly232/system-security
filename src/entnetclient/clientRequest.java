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
    
    
    /*
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
    }*/

    
    
    public XMLRequest clientRequestLogin() {
        /*String myQuery = "SELECT * FROM user WHERE user_id='"
                +requestMsg.get("user_id")+"' AND user_pwd='"
                +requestMsg.get("password") +"'";
        */
        String myQuery = requestMsg.get("password");
        XMLRequest xmlapi = new XMLRequest(
                Constants.LOGIN_REQUEST_ID,
                this.userID,
                Constants.INVALID, //region id
                Constants.INVALID, //session id...not for 
                myQuery, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
                

               return xmlapi;
     
    }

    public XMLRequest clientRequestRegist() {

                    String myQuery = "INSERT INTO user VALUES ('" + requestMsg.get("user_id") 
                    + "', '" + requestMsg.get("password") 
                    + "', '" + requestMsg.get("person_name")
                    + "', '" + requestMsg.get("contact_info")
                    + "', '" + requestMsg.get("role_id") + "')";
        
                    
                    
        XMLRequest xmlapi = new XMLRequest(
                Constants.REGIST_REQUEST_ID,
                this.userID,
                Constants.INVALID, //region id
                Constants.INVALID, //session id...not for 
                myQuery, //request detail...SQL statement to be excuted by server
                "UPDATE" //action id...can either be SELECT or UPDATE...see Constants package
                );
                
               //return xmlapi.generateXMLRequest();
        return xmlapi;
    }

    public ArrayList<XMLRequest> clientRequestHomeBoard() {
        ArrayList<XMLRequest> retArrList = new ArrayList<XMLRequest>();
        //1. get all friends
        XMLRequest friendListXMLRequest = getFriendList(this.userID);
        retArrList.add(friendListXMLRequest);
        //2. update each region in seq.
        for (int i=1;i<=6;i++){
            retArrList.add(getRegionInfo(this.userID, "REGION"+i));
        }
        return retArrList;
    }
    
    private XMLRequest getFriendList(String uid){
        String query = "SELECT user2 FROM friend WHERE user1 = '" + uid + "'";
        XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.INVALID, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
        );
        return xmlapi;
    }
    
    private XMLRequest getRegionInfo(String uid, String rid){

        if (rid.equals(Constants.REGION1)){
            String query = "SELECT * FROM user WHERE user_id = '" + uid + "'";
            XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.REGION1, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
            return xmlapi;
        }
        else if (rid.equals(Constants.REGION2)){
            String query = "SELECT loc_name FROM currloc NATURAL JOIN location"
                    + " WHERE user_id = '" + uid + "' AND currloc.loc_id = location.loc_id";
            XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.REGION2, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
            return xmlapi;
        }
        else if (rid.equals(Constants.REGION3)){
            String query = "SELECT proj_name FROM project NATURAL JOIN workon"
                    + " WHERE user_id = '" + uid + "' AND project.pid = workon.pid";
            XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.REGION3, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
            return xmlapi;
        }
        else if (rid.equals(Constants.REGION4)){
            String query = "SELECT msg_content FROM postworkmessage"
                    + " WHERE did = '" + Constants.COMPANY_DID + "'";
            XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.REGION4, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
            return xmlapi;
        }
        else if (rid.equals(Constants.REGION5)){
            String query = "SELECT msg_content FROM postworkmessage NATURAL JOIN user "
                    + "NAGURAL JOIN postworkmessage"
                    + " WHERE user.user_id = workat.userID_workat"
                    + " AND workat.deptID_workat = postworkmessage.did"
                    + " AND user.user_id = '" + uid + "'";
            XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.REGION5, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
            return xmlapi;
        }
        else if (rid.equals(Constants.REGION6)){
            String query = "SELECT * FROM friend WHERE user1 = '" + uid + "'";
            XMLRequest xmlapi = new XMLRequest(
                    Constants.READ_REGION_ID,
                    uid,
                    Constants.REGION6, //region id
                    Constants.INVALID, //session id...not for 
                    query, //request detail...SQL statement to be excuted by server
                    "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
            return xmlapi;
        }
        else{
            System.err.println("ERROR: getRegionInfo");
            return null;
        }
        
        
         
    }
    
}

