/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

//import XML.Constants;
import java.util.*;

import Constants.*;
import XML.*;

import Security.*;

/**
 *
 * @author Lin
 */
public class clientRequest {
    private HashMap<String,String> requestMsg;
    private String requestID;
    private String userID;
    private String k_db;
    public clientRequest(String reqID, String uID, HashMap<String,String> reqMsg, String kdb){
        this.requestID = reqID;
        this.requestMsg = reqMsg;
        this.userID = uID;
        this.k_db = kdb;
    }
    
    
    
    public XMLRequest clientRequestLogin() {
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

        
        
        /*
                    String myQuery = "INSERT INTO user VALUES ('" + requestMsg.get("user_id") 
                    + "', '" + requestMsg.get("password") 
                    //+ "', '" + requestMsg.get("person_name")
                    + "', '" + requestMsg.get("contact_info")
                    + "', " + requestMsg.get("role_id") + ");";
    */
        
     
        
        String myQuery = "INSERT INTO user (user_id, user_pwd, contact_info, role_id) VALUES ("
                + "AES_ENCRYPT('"+requestMsg.get("user_id")+"','"+k_db+"'), "
                + "AES_ENCRYPT('"+requestMsg.get("password")+"','"+k_db+"'), "
                + "AES_ENCRYPT('"+requestMsg.get("contact_info")+"','"+k_db+"'), "
                + "AES_ENCRYPT('"+requestMsg.get("role_id")+"','"+k_db+"'));";
       
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
        XMLRequest myFriReqXMLreq = getFriendNotify(this.userID);
        retArrList.add(myFriReqXMLreq);
        //2. update each region in seq.
        for (int i=1;i<=6;i++){
            retArrList.add(getRegionInfo(this.userID, "REGION"+i));            
        }
        return retArrList;
    }
    
    
    private XMLRequest getFriendNotify(String uid){
        String query = "SELECT F.user1 FROM friend F WHERE F.user2 = '" + uid + "'"
                + "AND F.user1 not in " +
                "(SELECT F2.user2 FROM friend F2 WHERE F2.user1 = '" + uid + "'"
                + "AND F2.user1 in ("
                + "SELECT F3.user2 FROM friend F3 WHERE F3.user1=F2.user2));";
        
        XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.NOTIFYREGION, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
        );
        return xmlapi;
    }
    
    
    
    private XMLRequest getFriendList(String uid){
        
        /*
        String query = "SELECT F.user2 FROM friend F WHERE F.user1 = '" + uid + "'"
                + "AND F.user1 in ("
                + "SELECT F2.user2 FROM friend F2 WHERE F2.user1=F.user2);";
        
         */
        
        
        //SELECT AES_DECRYPT(first_name, 'usa2010'), AES_DECRYPT(address, 'usa2010') from user;
        
        String query = "SELECT AES_DECRYPT(F.user2, '"+this.k_db+"') as F.user2 FROM friend F WHERE "
                + "AES_DECRYPT(F.user1,'"+this.k_db+"') = '" + uid + "'"
                + "AND AES_DECRYPT(F.user1,'"+this.k_db+"') in ("
                + "SELECT AES_DECRYPT(F2.user2,'"+this.k_db+"') "
                + "FROM friend F2 WHERE AES_DECRYPT(F2.user1,'"+this.k_db+"')=AES_DECRYPT(F.user2,'"+this.k_db+"'));";
        
        
        XMLRequest xmlapi = new XMLRequest(
                Constants.READ_REGION_ID,
                uid,
                Constants.FRIENDLISTREGION, //region id
                Constants.INVALID, //session id...not for 
                query, //request detail...SQL statement to be excuted by server
                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
        );
        return xmlapi;
    }
    
    private XMLRequest getRegionInfo(String uid, String rid){

        if (rid.equals(Constants.REGION1)){
            //String query = "SELECT user_id, contact_info FROM user WHERE user_id = '" + uid + "';";
            
            //SELECT AES_DECRYPT(first_name, 'usa2010'), AES_DECRYPT(address, 'usa2010') from user;
            
            String query = "SELECT AES_DECRYPT(user_id,'"+this.k_db+"') as user_id, "
                    + "AES_DECRYPT(contact_info,'"+this.k_db+"') as contact_info "
                    + "FROM user WHERE AES_DECRYPT(user_id,'"+this.k_db+"') = '" + uid + "';";
            
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
            //String query = "SELECT loc_name FROM currloc NATURAL JOIN location"
            //        + " WHERE user_id = '" + uid + "' AND currloc.loc_id = location.loc_id;";
            
            String query = "SELECT AES_DECRYPT(loc_name,'"+this.k_db+"') as loc_name FROM currloc NATURAL JOIN location"
                    + " WHERE AES_DECRYPT(user_id,'"+this.k_db+"') = '" 
                    + uid + "' AND AES_DECRYPT(currloc.loc_id,'"+this.k_db+"') = AES_DECRYPT(location.loc_id,'"+this.k_db+"');";
            
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
            //String query = "SELECT proj_name FROM project NATURAL JOIN workon"
            //        + " WHERE uid = '" + uid + "' AND project.proj_id = workon.pid;";
            
            String query = "SELECT AES_DECRYPT(proj_name,'"+this.k_db+"') as proj_name FROM project NATURAL JOIN workon"
                    + " WHERE AES_DECRYPT(uid,'"+this.k_db+"') = '" + uid 
                    + "' AND AES_DECRYPT(project.proj_id,'"+this.k_db+"') = AES_DECRYPT(workon.pid,'"+this.k_db+"');";
            
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
            //String query = "SELECT msg_id, msg_content FROM postworkmessage"
            //        + " WHERE did = " + Constants.COMPANY_DID + ";";
            
            
            String query = "SELECT AES_DECRYPT(msg_id,'"+this.k_db+"') as msg_id, "
                    + "AES_DECRYPT(msg_content,'"+this.k_db+"') as msg_content FROM postworkmessage"
                    + " WHERE AES_DECRYPT(did,'"+this.k_db+"') = " + Constants.COMPANY_DID + ";";
            
            
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
            /*
            String query = "SELECT msg_id, msg_content FROM postworkmessage NATURAL JOIN user "
                    + "NATURAL JOIN workat"
                    + " WHERE user.user_id = workat.userID_workat"
                    + " AND workat.deptID_workat = postworkmessage.did"
                    + " AND user.user_id = '" + uid + "';";
             */
            
            
            String query = "SELECT AES_DECRYPT(msg_id,'"+this.k_db+"') as msg_id, "
                    + "AES_DECRYPT(msg_content,'"+this.k_db+"') as msg_content FROM postworkmessage NATURAL JOIN user "
                    + "NATURAL JOIN workat"
                    + " WHERE AES_DECRYPT(user.user_id,'"+this.k_db+"') = AES_DECRYPT(workat.userID_workat,'"+this.k_db+"')"
                    + " AND AES_DECRYPT(workat.deptID_workat,'"+this.k_db+"') = AES_DECRYPT(postworkmessage.did,'"+this.k_db+"')"
                    + " AND AES_DECRYPT(user.user_id,'"+this.k_db+"') = '" + uid + "';";
            
            
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
            String query = "SELECT * FROM friend WHERE user1 = '" + uid + "';";
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

    XMLRequest clientRequestUpdateRegion(String regionID, String newContent, String uIDsrc) {
        
        String myQuery = "";
        if (regionID.equals(Constants.REGION1)){ //contact info
            myQuery = "UPDATE user SET contact_info = '" + newContent 
                    + "' WHERE user_id = '" + uIDsrc + "';";
        }
        else if (regionID.equals(Constants.REGION2)){ //location info
            myQuery = "UPDATE currloc SET loc_id = '" + newContent 
                    + "' WHERE user_id = '" + uIDsrc + "';";
            //TODO: GUI on region 2 should have a drop list of valid locations
        }
        else if (regionID.equals(Constants.REGION3)){ //current project
            myQuery = "UPDATE workon SET pid = '" + newContent 
                    + "' WHERE user_id = '" + uIDsrc + "';";
            //TODO: GUI on region 2 should have a drop list of valid locations
        }
        else if (regionID.equals(Constants.REGION4)){ //company announcement board
            //should not be allowed.
            //new company messages should be inserted. existing ones should not be edited
            System.err.println("ERROR: should not change existing company message");
        }
        else if (regionID.equals(Constants.REGION5)){ //department board
            //should not be allowed.
            //new dept messages should be inserted. existing ones should not be edited
            System.err.println("ERROR: should not change existing dept message");
        }
        else if (regionID.equals(Constants.REGION6)){ //friend board
            //should not be allowed.
            //new friend messages should be inserted. existing ones should not be edited
            System.err.println("ERROR: should not change existing friend message");
        }
        else{
            System.err.println("incorrect regionID for clientRequest::clientRequestUpdateRegion");
            return null;
        }

        
            XMLRequest xmlapi = new XMLRequest(
                Constants.UPDATE_REGION_ID,
                this.userID,
                regionID, //region id
                Constants.INVALID, //session id...not for 
                myQuery, //request detail...SQL statement to be excuted by server
                "UPDATE" //action id...can either be SELECT or UPDATE...see Constants package
                );

        return xmlapi;
    }
    
}

