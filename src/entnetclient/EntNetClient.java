/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

//import XML.Constants;
import XML.XMLRequest;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import Constants.*;
import XML.MyResultSet;
import XML.XMLRequest;

import java.nio.CharBuffer;
import java.rmi.server.UID;
import javax.sql.rowset.WebRowSet;
import view.*;


import Security.*;

import java.security.PrivateKey;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
//import view.MainUI;
/**
 * 
 * @author Lin
 */


public class EntNetClient {

	/**
	 * @param args
	 *            the command line arguments
	 */

        private static EntNetClient instance;    
        private ClientMain clientMain;
        private String thisUserID;
        private CommandLineClientTest test;
        private boolean commandline = false;
        
        private String k_db; //database passwrod used to perform encryption/decyption
        
        public String getThisUserID(){
            return thisUserID;
        }
        
        private EntNetClient(ClientMain cm){
            clientMain = cm;
            //TODO: change this to server's replied k_db:
            k_db = "DATABASEPASSWORD";
            establishSessionKey();
        }
        
        public EntNetClient(CommandLineClientTest t){
            test = t;
        }

        public static EntNetClient getInstance(ClientMain cm) {
            if (instance==null)
                return new EntNetClient(cm);
            else
                return instance;
        }
        
        
        
        ///////////////////////////////////

        public static MyKey sessionKey;
        public void establishSessionKey(){
            SecureRandom r = new SecureRandom();
            byte[] salt = new byte [8];
            //r.nextBytes(salt);
            int s = r.nextInt()%100;
            for (int i=0;i<salt.length;i++){
                salt[i] = (byte) ((int)salt[i] + 256);
            }
            String result = null;
            for (int i = 0; i < salt.length; i++) {
		result += Integer.toHexString((0x000000ff & salt[i]) | 0xffffff00).substring(6);
            }

            SharedKey sk = SharedKey.getInstance();

            sessionKey = sk.generateKeyWithPwd("lin");
            XMLRequest.sessionKey = sessionKey;

         
            //String sessionkeyencrypted = new String(mypki.encrypt("lin", pubkey));

            

            //System.out.println("sessionkeyencrypted="+sessionkeyencrypted.length());
            

            XMLRequest xmlreq = new XMLRequest(Constants.SESSION_KEY_EST,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.SESSION_KEY_EST,
                    Constants.INVALID);

            invokeRequestThread(xmlreq);
        }
        
        
        public void clientRegist(
              //  String VerificationCode, 
                String Username,
                String Password,
              //  String RealName,
                String ContactInfo,
                String Role_ID 
                        ) 
        {
		HashMap<String, String> RegistCredential = new HashMap<String, String>();
		try {
                  //  RegistCredential.put("ver_code", VerificationCode);
                    RegistCredential.put("user_id", Username);
                    RegistCredential.put("password", Password);
                  //  RegistCredential.put("person_name", RealName);
                    RegistCredential.put("contact_info", ContactInfo);
                    RegistCredential.put("role_id", Role_ID);
                } catch (Exception e) {
		}
		;

		clientRequest registRequest = new clientRequest(
				Constants.REGIST_REQUEST_ID, Username, RegistCredential, this.k_db);
		//String retXML = registRequest.generateXMLforRequest();
		XMLRequest xmlr = registRequest.clientRequestRegist();
                        
                invokeRequestThread(xmlr);
	}
        
        public void quitClient(){
        	XMLRequest xmlRequest = new XMLRequest(Constants.QUIT_ID, 
        			  thisUserID,Constants.FRIENDLISTREGION, null, Constants.QUIT_ID, Constants.UPDATE);
        	  invokeRequestThread(xmlRequest);
                  //System.exit(0);
        	  if (commandline) {
				
			} else {
                this.clientMain.quit();
			}
        }
	
        public void deleteFriend(String friend_id){
        	String sqlQueryString = "DELETE FROM friend where user1 = \"" + thisUserID + 
        													"\" AND user2 = \"" + friend_id + "\";";
          	XMLRequest xmlRequest = new XMLRequest(Constants.DELETE_FRIEND_ID, 
          				thisUserID,Constants.FRIENDLISTREGION, null, sqlQueryString, Constants.UPDATE);
          		invokeRequestThread(xmlRequest);
        }
        
        public void friendRequest(String friend_id){
      	  String sqlQueryString = "insert into friend values(\"" + thisUserID + "\",\"" + friend_id + 
      			  									"\",\"" + Constants.ADD_FRIEND_ID + "\",null);";
      	  XMLRequest xmlRequest = new XMLRequest(Constants.ADD_FRIEND_ID, 
      			  thisUserID,Constants.FRIENDLISTREGION, null, sqlQueryString, Constants.UPDATE);
      	  invokeRequestThread(xmlRequest);
        }
        
        public void postMessage(String friend_id, String messageString){
        	  String sqlQueryString = "insert into friend values(\"" + friend_id + "\",\"" + thisUserID + 
							"\",\"" + messageString + "\",null);";
        	  XMLRequest xmlRequest = new XMLRequest(Constants.UPDATE_REGION_ID, 
        			  thisUserID,Constants.FRIENDLISTREGION, null, sqlQueryString, Constants.UPDATE);
        	  invokeRequestThread(xmlRequest);
        }
        
        /*
        public void getFriendList(){
        	  String sqlQueryString = null;//"insert into friend values("tao","lin","hello",null);";
        	  XMLRequest xmlRequest = new XMLRequest(Constants.READ_REGION_ID, 
        			  thisUserID,Constants.FRIENDLISTREGION, null, sqlQueryString, Constants.SELECT);
          	  invokeRequestThread(xmlRequest);
        }*/
        
        private XMLRequest getFriendList(String uid){
        String query = "SELECT F.user2 FROM friend F WHERE F.user1 = '" + uid + "'"
                + "AND F.user1 in ("
                + "SELECT F2.user2 FROM friend F2 WHERE F2.user1=F.user2);";
        
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

        public void clientLogin(String tmp_uid, String tmp_pwd)
			throws IOException {
                        
		HashMap<String, String> loginCredential = new HashMap<String, String>();
		loginCredential.put("user_id", tmp_uid); 
		loginCredential.put("password", tmp_pwd); 
		clientRequest loginRequest = new clientRequest(
				Constants.LOGIN_REQUEST_ID, tmp_uid, loginCredential, this.k_db);
		XMLRequest xmlr= loginRequest.clientRequestLogin();
                
                
                System.out.println("client session key: "+xmlr.sessionKey.skey);
                
                invokeRequestThread(xmlr);
                
                

	}
        
        
        public void  clientViewOtherPersonBoard(String otherPersonUid, String switchBoardCode) throws IOException{
            
            //general logic: close current MainUI, then open a new MainUI populated based on the retXML
            
            ArrayList<XMLRequest> otherPersonBoardInfoXML = clientHomeBoardRequest(otherPersonUid);
            
            XMLRequest myFriendListXMLreq = getFriendList(this.thisUserID);
            
            
            otherPersonBoardInfoXML.set(0, myFriendListXMLreq); 

            
            //close the current ui, then open a new ui
            //there are 3 possible senarios to switch:
            //1. my home board -> other person's home board
            //2. other person's home board -> other person's home board
            //3. other person's home board -> my home board
            
            if (commandline) {
				
			} 
            else {
				if (switchBoardCode.equals(Constants.HOME_TO_OTHER_VIEW)){
	                clientMain.HomeToPerson();
	            }
	            else if (switchBoardCode.equals(Constants.OTHER_TO_OTHER_VIEW)){
	                clientMain.PersonToPerson();
	            }
	            else if (switchBoardCode.equals(Constants.OTHER_TO_HOME_VIEW)){
	                clientMain.PersonToHome();
	            }
	            else {
	                System.err.println("ERROR: invalid switchBoardCode in EntNetClient::clientViewOtherPersonBoard");
	                return;
	            }
			}
            
            
            
            for (int i=0;i<otherPersonBoardInfoXML.size();i++){
                invokeRequestThread(otherPersonBoardInfoXML.get(i));
            }

        }
       
        
        public void clientUpdateRegion(String regionID, String newContent){
                HashMap<String, String> updateCredential = new HashMap<String, String>();
		updateCredential.put("regionID", regionID); 
		updateCredential.put("newContent", newContent); 
		clientRequest updateRegionRequest = new clientRequest(
				Constants.UPDATE_REGION_ID, this.thisUserID, updateCredential, this.k_db);
		XMLRequest xmlr= updateRegionRequest.clientRequestUpdateRegion(regionID, newContent, this.thisUserID);
                invokeRequestThread(xmlr);
        }
        
        
        
        private ArrayList<XMLRequest> clientHomeBoardRequest(String uid)
        {
            HashMap<String, String> homeBoardRequestInfo = new HashMap<String, String>();
            //homeBoardRequestInfo.put("user_id",uid);
            clientRequest homeBoardRequest = new clientRequest(
				Constants.READ_REGION_ID, uid, homeBoardRequestInfo, this.k_db); 
            ArrayList<XMLRequest> al_xmlr= homeBoardRequest.clientRequestHomeBoard();
            //XMLRequest myFriReqXMLreq = getFriendNotify(this.thisUserID);
            //al_xmlr.add(myFriReqXMLreq);
            return al_xmlr;
        }
        
        public void returnUserHomePage() {
                

                if (commandline) {
					
				} else {
	                this.clientMain.PersonToHome();
				}  
                
                ArrayList<XMLRequest> xmlreq = clientHomeBoardRequest(this.thisUserID);
                for (int i=0;i<xmlreq.size();i++){
                        invokeRequestThread(xmlreq.get(i));
                    }

        }
    
        

    private void invokeRequestThread(XMLRequest xmlr) {
    	if (commandline) {
            requestHandler rh = new requestHandler(xmlr, this);
            rh.run();
		} else {
	        requestHandler rh = new requestHandler(xmlr, this);
	        Thread t = new Thread(rh);
	        t.start();
		}
    }
    

    
    
    public void requestThreadCallBack(XMLRequest xmlreq) throws SQLException, InterruptedException{
        if (xmlreq.getRequestID().equals(Constants.LOGIN_REQUEST_ID)){
            //check if the login is successful
            String LoginStat = xmlreq.getRequestDetail();
            if (LoginStat.equals(Constants.TRUE)){
                //successful login
                //goto user's home page by asking server to send xml of user homeboard
                try{
                    ArrayList<XMLRequest> homeBoardInfoXML 
                            = clientHomeBoardRequest(xmlreq.getUserID());
                    this.thisUserID = xmlreq.getUserID();

                    //populate screen: close loginUI, open new UI
                    if (commandline) {
						test.LoginCallBack(xmlreq);
					}else {
                    this.clientMain.LoginToHome();

					}
                    
                    //now we are in homepage...load home board content
                    for (int i=0;i<homeBoardInfoXML.size();i++){
                        invokeRequestThread(homeBoardInfoXML.get(i));
                    }

                    
                }catch(Exception e){};
            }
            else{
            	if (commandline) {
					test.LoginCallBack(xmlreq);
				} else {

				}
                     return;           
            }
        } //end of login request threadCallBack
                
        
        else if (xmlreq.getRequestID().equals(Constants.SESSION_KEY_EST)){
            String SessionKeyEstStat = xmlreq.getRequestDetail();
            if (SessionKeyEstStat==Constants.TRUE){
                XMLRequest.sessionKey = this.sessionKey;
                
                System.out.println("session key successf");
            }
            else{
                return;
            }
        }
        
        
        else if (xmlreq.getRequestID().equals(Constants.REGIST_REQUEST_ID)){
            //check if the login is successful
            String rowAffected = xmlreq.getRequestDetail();
            if (rowAffected.equals("1")){
                //successful registration
                //goto user's home page by asking server to send xml of user homeboard
                try{
                   /* ArrayList<XMLRequest> homeBoardInfoXML 
                            = clientHomeBoardRequest(xmlreq.getUserID());
                    //populate screen: close loginUI, open new UI
                    this.clientMain.LoginToHome();
                    
                    //now we are in homepage...load home board content
                    for (int i=0;i<homeBoardInfoXML.size();i++){
                        invokeRequestThread(homeBoardInfoXML.get(i));
                    }
                     */
                	view.LoginUI.checkRegist(true);
                return; 
                }
                catch(Exception e){
                };
            }
            else{
                //failed registration
            	view.LoginUI.checkRegist(false);
                return;
            }
        } //end of regist request threadCallBack
        else if (xmlreq.getRequestID().equals(Constants.READ_REGION_ID)){
            //TODO: this is for home board loading and other refresh page operations
            //      need to use Tao's MyResultSet
            //TODO: send to UI (to clientMain) a hashmap containing:
            // 1. region ID
            // 2. region content
            ArrayList<String> resultSetArrayList = new ArrayList<String>();
            String regionID = xmlreq.getRegionID();
            MyResultSet myRS;
            if (xmlreq.getRequestDetail().equals(Constants.RETURN_RESULTSET))
                myRS = xmlreq.getMyResultSet();
            else{
                System.err.println("ERROR: entnetclient callback readregion did not return myresultset");
                return;
            }
            
            if (regionID.equals(Constants.FRIENDLISTREGION)){
                for (int i=0;i<myRS.getTable().size();i++){

                    String friendUID = myRS.getStringValue(i, "user2");
                    resultSetArrayList.add(friendUID);
                }
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
                
                if (commandline) {
					//test.personPanelCallback(Constants.FRIENDLISTREGION, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}

                //this.clientMain.LoginToHome();
            }
            
            else if (regionID.equals(Constants.NOTIFYREGION)){
                for (int i=0;i<myRS.getTable().size();i++){
                    String friRequest = myRS.getStringValue(i, "user1");
                    resultSetArrayList.add(friRequest);
                    System.out.println("testing");
                    System.out.println(friRequest);
                }
                if (commandline) {
					//test.personPanelCallback(Constants.FRIENDLISTREGION, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
	                System.out.println("=======FRIENDNOTIFY TESTING======="+resultSetArrayList);
				}
            }
            
            else if (regionID.equals(Constants.REGION1)){
                String contact_info = myRS.getStringValue(0, "contact_info");
                String uid = myRS.getStringValue(0,"user_id");
                resultSetArrayList.add(uid);
                resultSetArrayList.add(contact_info);
                if (commandline) {
					test.personPanelCallback(Constants.REGION1, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
            }
            else if (regionID.equals(Constants.REGION2)){
                String currLocName = myRS.getStringValue(0, "loc_name");
                resultSetArrayList.add(currLocName);
                //resultSetHashMap.put("loc_name", currLocName);
                if (commandline) {
					test.personPanelCallback(Constants.REGION2, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
            }
            else if (regionID.equals(Constants.REGION3)){
                String currProj = myRS.getStringValue(0, "proj_name");
                resultSetArrayList.add(currProj);
                //resultSetHashMap.put("proj_name", currProj);
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
                if (commandline) {
					test.personPanelCallback(Constants.REGION3, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}

            }
            else if (regionID.equals(Constants.REGION4)){
                for (int i=0;i<myRS.getTable().size();i++){
                    //String msg_id = myRS.getStringValue(i, "msg_id"); //msg_id, msg_content
                    String msg_content = myRS.getStringValue(i, "msg_content");
                    resultSetArrayList.add(msg_content);
                    System.out.println("company msg = "+msg_content);
                    //resultSetHashMap.put(msg_id, msg_content);
                }
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
                if (commandline) {
					test.personPanelCallback(Constants.REGION4, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
            }
            else if (regionID.equals(Constants.REGION5)){
                for (int i=0;i<myRS.getTable().size();i++){
                    String msg_id = myRS.getStringValue(i, "msg_id"); //msg_id, msg_content
                    String msg_content = myRS.getStringValue(i, "msg_content");
                    resultSetArrayList.add(msg_content);
                    //resultSetHashMap.put(msg_id, msg_content);
                }
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
                if (commandline) {
					test.personPanelCallback(Constants.REGION5, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}

            }
            else if (regionID.equals(Constants.REGION6)){
                for (int i=0;i<myRS.getTable().size();i++){
                    String msg_id = myRS.getStringValue(i, "msg_id"); //msg_id, msg_content
                    String msg_content = myRS.getStringValue(i, "message");
                    resultSetArrayList.add(msg_content);
                    //resultSetHashMap.put(msg_id, msg_content);
                }
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
                if (commandline) {
					test.personPanelCallback(Constants.REGION6, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
            }
            else{
                System.err.println("requestThreadCallBack ERROR: cannot identify region id");
                return;
            }

        }else if (xmlreq.getRequestID().equals(Constants.ADD_FRIEND_ID)) {
            String rowAffected = xmlreq.getRequestDetail();
            if (rowAffected.equals("1")) {
				System.out.println("Add friend success");
			}else{
				System.out.println("Add friend fail");
			}
		}
        else if (xmlreq.getRequestID().equals(Constants.DELETE_FRIEND_ID)) {
            String rowAffected = xmlreq.getRequestDetail();
            int rows  = Integer.parseInt(rowAffected);
				System.out.println("Delete friend success");
		}
        else if (xmlreq.getRequestID().equals(Constants.UPDATE_REGION_ID)){
            String rowAffected = xmlreq.getRequestDetail();
            if (rowAffected.equals("1")){
                //update successful. do nothing
                System.out.println("update region successful");
                
            }
            else{
                //failed registration
                System.err.println("ERROR: requestThreadCallBack cannot update region");
                return;
            }
            
        }
    }

    
}
