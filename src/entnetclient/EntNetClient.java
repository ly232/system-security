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


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class EntNetClient {

        private static EntNetClient instance;    
        private ClientMain clientMain;
        private String thisUserID;
        private CommandLineClientTest test;
        private boolean commandline = false;
        public String roleID;
        public PublicKey K_server;
        public String k_session;
        
        
        public String getThisUserID(){
            return thisUserID;
        }
        
        private EntNetClient(ClientMain cm){
            clientMain = cm;
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
        
        public void getServerPublicKey(){
        	System.out.println("getting server's public key...");
        	
        	XMLRequest xmlreq = new XMLRequest(Constants.REQ_SERVER_PUBKEY,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.REQ_SERVER_PUBKEY,
                    Constants.INVALID);
        	invokeRequestThread(xmlreq);
        }
        
        public void establishSessionKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        	SecureRandom r = new SecureRandom();
        	Integer s = r.nextInt();
        	k_session = s.toString();
        	//now send encrypted sessionKeyGenStr and cipher to server
        	MyPKI mypki = MyPKI.getInstance();
        	byte[] cipher_sessionKeyGenStr = mypki.rsaEncrypt(K_server, k_session.getBytes());
        	XMLRequest xmlreq = new XMLRequest(Constants.SESSION_KEY_EST,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.SESSION_KEY_EST,
                    Constants.INVALID);
        	xmlreq.cipher_sessionKey = cipher_sessionKeyGenStr;
        	invokeRequestThread(xmlreq);
        }
        
        
        
        
        public void clientRegist(
        		String uname, 
        		String pwd, 
        		String contactinfo, 
        		String roleID, 
        		String deptID,
        		String locID,
        		String projID,
        		String vcode
        ) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        	XMLRequest xmlr = new XMLRequest(
        			Constants.REGIST_REQUEST_ID,
        			this.thisUserID,
        			Constants.INVALID, //region id
        			Constants.INVALID, //session id
        			Constants.INVALID, //request detail
        			"UPDATE");
        	SharedKey sk = SharedKey.getInstance();
        	xmlr.requestData.put("user_id", sk.sessionKeyEncrypt(this.k_session, uname));
        	xmlr.requestData.put("password", sk.sessionKeyEncrypt(this.k_session, pwd));
        	xmlr.requestData.put("contact_info", sk.sessionKeyEncrypt(this.k_session, contactinfo));
        	xmlr.requestData.put("role_id", sk.sessionKeyEncrypt(this.k_session, roleID));
        	xmlr.requestData.put("dept_id", sk.sessionKeyEncrypt(this.k_session, deptID));
        	xmlr.requestData.put("loc_id", sk.sessionKeyEncrypt(this.k_session, locID));
        	xmlr.requestData.put("proj_id", sk.sessionKeyEncrypt(this.k_session, projID));
        	xmlr.requestData.put("vcode", sk.sessionKeyEncrypt(this.k_session, vcode));
        	invokeRequestThread(xmlr);
        }
        
        public void clientLogin(String tmp_uid, String tmp_pwd) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        	XMLRequest xmlr = new XMLRequest(
        			Constants.LOGIN_REQUEST_ID,
        			this.thisUserID,
        			Constants.INVALID, //region id
        			Constants.INVALID, //session id
        			Constants.INVALID, //request detail
        			"SELECT");
        	SharedKey sk = SharedKey.getInstance();
        	xmlr.requestData.put("user_id", sk.sessionKeyEncrypt(this.k_session, tmp_uid));
        	xmlr.requestData.put("password", sk.sessionKeyEncrypt(this.k_session, tmp_pwd));
        	invokeRequestThread(xmlr);
        }
        
        private ArrayList<XMLRequest> clientHomeBoardRequest(String uid)
        {
        	ArrayList<XMLRequest> retArrList = new ArrayList<XMLRequest>();
        	//get list of current friends:

        	XMLRequest friendListXMLRequest = getFriendList(uid);
        	retArrList.add(friendListXMLRequest);
        	//get friend requests:
        	XMLRequest myFriReqXMLreq = new XMLRequest(
                    Constants.READ_REGION_ID,
                    uid,
                    Constants.NOTIFYREGION, //region id
                    Constants.INVALID, //session id...not for 
                    Constants.INVALID, //request detail...SQL statement to be excuted by server
                    "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
            );
        	retArrList.add(myFriReqXMLreq);
        	//load each of the rest 6 regions:
            for (int i=1;i<=6;i++){
            	XMLRequest xmlr = new XMLRequest(
                        Constants.READ_REGION_ID,
                        uid,
                        "REGION"+i, //region id
                        Constants.INVALID, //session id...no longer used
                        Constants.INVALID,
                        "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
                    );
            	
                retArrList.add(xmlr);            
            }

            return retArrList;
        }
 
        public void quitClient(){
        	XMLRequest xmlRequest = new XMLRequest(
        			Constants.QUIT_ID, 
        			thisUserID,
        			Constants.INVALID, 
        			Constants.INVALID, 
        			Constants.INVALID, 
        			Constants.INVALID
        			);
        	  invokeRequestThread(xmlRequest);
        	  if (commandline) {
				
			} else {
                this.clientMain.quit();
			}
        }
	
        public void deleteFriend(String friend_id) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
          		XMLRequest xmlRequest = new XMLRequest(
          				Constants.DELETE_FRIEND_ID, 
          				thisUserID,
          				Constants.FRIENDLISTREGION, 
          				Constants.INVALID, 
          				Constants.INVALID, 
          				Constants.UPDATE);
          		SharedKey sk = SharedKey.getInstance();
          		xmlRequest.requestData.put("deleteFriendID", sk.sessionKeyEncrypt(this.k_session, friend_id));
          		
          		invokeRequestThread(xmlRequest);
        }
        
        
        
        public void friendRequest(String friend_id) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
      	  XMLRequest xmlRequest = new XMLRequest(
      			  Constants.ADD_FRIEND_ID, 
      			  thisUserID,
      			  Constants.NOTIFYREGION, 
      			  Constants.INVALID, 
      			  Constants.INVALID, 
      			  Constants.UPDATE);
      	  SharedKey sk = SharedKey.getInstance();
      	  xmlRequest.requestData.put("requestFriendID", 
      			  sk.sessionKeyEncrypt(this.k_session, friend_id));
      	  invokeRequestThread(xmlRequest);
        }
     
        
        public void updateDeptBoard(String Mes){
    		XMLRequest xmlRequest = new XMLRequest(  
        			  Constants.UPDATE_REGION_ID, 
          			  thisUserID,
          			  Constants.REGION5, 
          			  Constants.INVALID, 
          			  Constants.INVALID, 
          			  Constants.UPDATE);
        	  SharedKey sk = SharedKey.getInstance();
          	  try {
				xmlRequest.requestData.put("Message", 
						  sk.sessionKeyEncrypt(this.k_session, Mes));
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          	  invokeRequestThread(xmlRequest);	
    }
        
        public void updateCompanyBoard(String companyMes){
        		XMLRequest xmlRequest = new XMLRequest(  
            			  Constants.UPDATE_REGION_ID, 
              			  thisUserID,
              			  Constants.REGION4, 
              			  Constants.INVALID, 
              			  Constants.INVALID, 
              			  Constants.UPDATE);
            	  SharedKey sk = SharedKey.getInstance();
              	  try {
					xmlRequest.requestData.put("Message", 
							  sk.sessionKeyEncrypt(this.k_session, companyMes));
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidAlgorithmParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              	  invokeRequestThread(xmlRequest);	
        }
        
        
        
        public void postMessage(String friend_id, String messageString) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        	  XMLRequest xmlRequest = new XMLRequest(
        			  Constants.UPDATE_REGION_ID, 
        			  thisUserID,
        			  Constants.REGION6, 
        			  SharedKey.getHash(messageString), 
        			  Constants.POST_FRIEND_MESSAGE, 
        			  Constants.UPDATE);
        	  SharedKey sk = SharedKey.getInstance();
        	  xmlRequest.requestData.put("postedFriendID", 
          			  sk.sessionKeyEncrypt(this.k_session, friend_id));
        	  xmlRequest.requestData.put("postedFriendMessage", 
          			  sk.sessionKeyEncrypt(this.k_session, messageString));
        	  invokeRequestThread(xmlRequest);
        }
        
        /////////////
        //can only be executed by the boss.
        //the ui will have a post company message button enabled for boss only.
        //so access control is realized by ui
        public void postCompanyMessage(String messageString) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        	
        	XMLRequest xmlRequest = new XMLRequest(
      			  Constants.UPDATE_REGION_ID, 
      			  thisUserID,
      			  Constants.REGION4, 
      			  SharedKey.getHash(messageString),//this field was intended to use for session ID. now we store hash
      			  Constants.POST_COMPANY_MESSAGE, 
      			  Constants.UPDATE);
        	SharedKey sk = SharedKey.getInstance();
      	  xmlRequest.requestData.put("postedCompnayMessage", 
        			  sk.sessionKeyEncrypt(this.k_session, messageString));
      	  invokeRequestThread(xmlRequest);
        }
        public void postDeptMessage(String messageString) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        	XMLRequest xmlRequest = new XMLRequest(
      			  Constants.UPDATE_REGION_ID, 
      			  thisUserID,
      			  Constants.REGION5, 
      			  SharedKey.getHash(messageString),
      			  Constants.POST_DEPT_MESSAGE, 
      			  Constants.UPDATE);
      	  SharedKey sk = SharedKey.getInstance();
      	  xmlRequest.requestData.put("postedDeptMessage", 
        			  sk.sessionKeyEncrypt(this.k_session, messageString));
      	  invokeRequestThread(xmlRequest);
        }

        private XMLRequest getFriendList(String uid){
	        XMLRequest xmlapi = new XMLRequest(
	                Constants.READ_REGION_ID,
	                uid,
	                Constants.FRIENDLISTREGION, //region id
	                Constants.INVALID, //session id...not for 
	                Constants.INVALID, //request detail...SQL statement to be excuted by server
	                "SELECT" //action id...can either be SELECT or UPDATE...see Constants package
	        );
	        return xmlapi;
        }

        public void getAllValidLocation(){
        	XMLRequest xmlRequest = new XMLRequest(
      				Constants.READ_REGION_ID, 
      				thisUserID,
      				Constants.VALID_LOCATION, 
      				Constants.INVALID, //session
      				Constants.INVALID, //request detail
      				Constants.SELECT
      				);
        	invokeRequestThread(xmlRequest);
        }
        public void getAllValidProject(){
        	XMLRequest xmlRequest = new XMLRequest(
      				Constants.READ_REGION_ID, 
      				thisUserID,
      				Constants.VALID_PROJECT, 
      				Constants.INVALID, //session
      				Constants.INVALID, //request detail
      				Constants.SELECT
      				);
        	invokeRequestThread(xmlRequest);
        }
        public void getAllValidDepartment(){
        	XMLRequest xmlRequest = new XMLRequest(
      				Constants.READ_REGION_ID, 
      				thisUserID,
      				Constants.VALID_DEPT, 
      				Constants.INVALID, //session
      				Constants.INVALID, //request detail
      				Constants.SELECT
      				);
        	invokeRequestThread(xmlRequest);
        }
        public void switchDeptBoard(String dname){
        	XMLRequest xmlRequest = new XMLRequest(
      				Constants.READ_REGION_ID, 
      				thisUserID,
      				Constants.SWITCH_DEPT,
      				Constants.INVALID, //session
      				dname, //request detail
      				Constants.SELECT
      				);
        	invokeRequestThread(xmlRequest);
        }
        
        //refresh user home board:
        public void refreshHomeBoard(){

        	ArrayList<XMLRequest> homeBoardInfoXML = this.clientHomeBoardRequest(thisUserID);
        	//now we are in homepage...load home board content
            for (int i=0;i<homeBoardInfoXML.size();i++){
                invokeRequestThread(homeBoardInfoXML.get(i));
            }
        	
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
       
        
        public void clientUpdateRegion(String regionID, String newContent) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
            
        	XMLRequest xmlr = new XMLRequest(
        			Constants.UPDATE_REGION_ID,
        			this.thisUserID,
        			regionID,
        			Constants.INVALID,
        			regionID,
        			"UPDATE"
        	);
        	SharedKey sk = SharedKey.getInstance();
        	xmlr.requestData.put("newContent", 
        			sk.sessionKeyEncrypt(this.k_session, newContent));
        	invokeRequestThread(xmlr);

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
    

    public void requestThreadCallBack(XMLRequest xmlreq) throws SQLException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        if (xmlreq.getRequestID().equals(Constants.LOGIN_REQUEST_ID)){
            String LoginStat = xmlreq.getRequestDetail();
            if (LoginStat.equals(Constants.TRUE)){
                try{
                	this.roleID = xmlreq.getSessionID();
                	this.thisUserID = xmlreq.getUserID();
                    ArrayList<XMLRequest> homeBoardInfoXML 
                            = clientHomeBoardRequest(this.thisUserID);
                    
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
                    //get all valid locations and projects to populate the droplist:
                    this.getAllValidLocation();
                    this.getAllValidProject();
                    this.getAllValidDepartment();
                    
                }catch(Exception e){};
            }
            else{ //login failed
            	if (LoginStat.equals(Constants.TOO_MANY_LOGIN_TRAILS)){
            		LoginUI.loginPopUp("Too many login failures. Client program shut down.");
            		System.exit(0);
            	}
            	else{
	            	if (commandline) {
						test.LoginCallBack(xmlreq);
					} else {
						//System.out.println("requestThreadCallBack: login failed");
						LoginUI.loginPopUp("Login failed!");
					}
	            }
                return;           
            }
        } //end of login request threadCallBack
                
        
        
        else if (xmlreq.getRequestID().equals(Constants.REGIST_REQUEST_ID)){
            //check if the login is successful
            String reg_stat = xmlreq.getRequestDetail();
            if (reg_stat.equals(Constants.REGISTRATION_SUCCESS)){
                //successful registration
                //goto user's home page by asking server to send xml of user homeboard
                try{
                	view.LoginUI.checkRegist(true, "");
                	return; 
                }
                catch(Exception e){
                };
            }
            else{
                //failed registration
            	String errMsg = "";
            	
            	if (xmlreq.getRequestDetail().equals(Constants.INVALID_PASSWORD_SETUP))
            		errMsg = "Incorrect password format--must contain at least a letter and a number.";
            	else if (xmlreq.getRequestDetail().equals(Constants.INVALID_VCODE))
            		errMsg = "Incorrect verification code.";
            	else if (xmlreq.getRequestDetail().equals(Constants.CANNOT_REGIST_AS_BOSS))
            		errMsg = "Cannot register as boss. See system admin for boss-role registration.";
            	else
            		errMsg = "Unknown.";
            	
            	view.LoginUI.checkRegist(false, errMsg);
            	
                return;
            }
        } //end of regist request threadCallBack
        else if (xmlreq.getRequestID().equals(Constants.READ_REGION_ID)){
            ArrayList<String> resultSetArrayList = new ArrayList<String>();
            String regionID = xmlreq.getRegionID();
            MyResultSet myRS;
            if (xmlreq.getRequestDetail().equals(Constants.RETURN_RESULTSET)){
                myRS = xmlreq.getMyResultSet();
                //integrity checking for myRS:
                Boolean integrity = SharedKey.checkHash(xmlreq.getActionID(), xmlreq.getSessionID());
                if (!integrity){
                	System.err.println("my result set integrity violation at EntNetClient:requestThreadCallBack");
                	return;
                }
            }
            else{
                System.err.println("ERROR: entnetclient callback readregion did not return myresultset");
                return;
            }
            
            if (regionID.equals(Constants.FRIENDLISTREGION)){
                for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                	//System.out.println("received cipher = " + myRS.getCipherValue(i, "user2"));
                    String friendUID = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "user2")));
                    resultSetArrayList.add(friendUID);
                }
                
                if (commandline) {
					//test.personPanelCallback(Constants.FRIENDLISTREGION, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}

            }
            
            else if (regionID.equals(Constants.NOTIFYREGION)){
                for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String friendUID = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "user1")));
                    resultSetArrayList.add(friendUID);

                }
                if (commandline) {
					//test.personPanelCallback(Constants.FRIENDLISTREGION, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
            }
            
            else if (regionID.equals(Constants.REGION1)){
            	SharedKey sk = SharedKey.getInstance();
            	String contact_info = 
                	new String(sk.sessionKeyDecrypt(this.k_session, 
                			myRS.getCipherValue(0, "contact_info")));
            	String uid = 
                	new String(sk.sessionKeyDecrypt(this.k_session, 
                			myRS.getCipherValue(0, "user_id")));
            	

                resultSetArrayList.add(uid);
                resultSetArrayList.add(contact_info);
                if (commandline) {
					test.personPanelCallback(Constants.REGION1, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
            }
            else if (regionID.equals(Constants.REGION2)){
            	SharedKey sk = SharedKey.getInstance();
            	String currLocName = 
                	new String(sk.sessionKeyDecrypt(this.k_session, 
                			myRS.getCipherValue(0, "loc_name")));
                //String currLocName = myRS.getStringValue(0, "loc_name");
                resultSetArrayList.add(currLocName);
                //resultSetHashMap.put("loc_name", currLocName);
                if (commandline) {
					test.personPanelCallback(Constants.REGION2, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);

				}
            }
            else if (regionID.equals(Constants.REGION3)){
            	SharedKey sk = SharedKey.getInstance();
            	String currProj = 
                	new String(sk.sessionKeyDecrypt(this.k_session, 
                			myRS.getCipherValue(0, "proj_name")));
            	
                resultSetArrayList.add(currProj);
                if (commandline) {
					test.personPanelCallback(Constants.REGION3, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}

            }
            else if (regionID.equals(Constants.REGION4)){
                for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String msg_content = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "msg_content")));
                    resultSetArrayList.add(msg_content);
                }
                if (commandline) {
					test.personPanelCallback(Constants.REGION4, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
            }
            else if (regionID.equals(Constants.REGION5)){
                for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String msg_content = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "msg_content")));
                    resultSetArrayList.add(msg_content);
                }
                if (commandline) {
					test.personPanelCallback(Constants.REGION5, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}

            }
            else if (regionID.equals(Constants.REGION6)){
                for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String msg_content = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "message")));
                    String msg_sender = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "user2")));
                    
                    resultSetArrayList.add(msg_content+" (FROM: "+msg_sender+")");
                }
                if (commandline) {
					test.personPanelCallback(Constants.REGION6, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
				}
            }
            else if (regionID.equals(Constants.VALID_LOCATION)){
            	for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String loc_name = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "loc_name")));
                    resultSetArrayList.add(loc_name);
                }
            	this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
            	
            }
            else if (regionID.equals(Constants.VALID_PROJECT)){
            	for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String proj_name = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "proj_name")));
                    resultSetArrayList.add(proj_name);
                }
            	
            	this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
            }
            else if (regionID.equals(Constants.VALID_DEPT)){
            	for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String dname = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "dname")));
                    resultSetArrayList.add(dname);
                }
            	
            	this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
            }
            else if (regionID.equals(Constants.SWITCH_DEPT)){
            	for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String msg_content = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "msg_content")));
                    resultSetArrayList.add(msg_content);
                }
                if (commandline) {
					test.personPanelCallback(Constants.REGION5, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, Constants.REGION5);
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
				this.refreshHomeBoard();
			}else{
				System.out.println("Add friend fail");
			}
		}
        else if (xmlreq.getRequestID().equals(Constants.DELETE_FRIEND_ID)) {
            String rowAffected = xmlreq.getRequestDetail();
            if (rowAffected.equals("2")) {//2 becuase friends relation is bidirectional
				System.out.println("delete friend success");
				this.refreshHomeBoard();
			}else{
				System.out.println("delete friend fail");
			}
		}
        else if (xmlreq.getRequestID().equals(Constants.UPDATE_REGION_ID)){
            String rowAffected = xmlreq.getRequestDetail();
            if (rowAffected.equals("1")){ 
                System.out.println("update region successful");
                
                if (xmlreq.getActionID().equals(Constants.POST_FRIEND_MESSAGE)==false)
                	this.refreshHomeBoard();

            }
            else{
                System.err.println("ERROR: requestThreadCallBack cannot update region");
                return;
            }
            
        }
    }

    
}
