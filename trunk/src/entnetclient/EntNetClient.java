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
        public static MyKey sessionKey;
        
        private String k_db; //database passwrod used to perform encryption/decyption
        
        //Lin-4/11/12
        public PublicKey K_server;
        public String k_session;
        
        
        public String getThisUserID(){
            return thisUserID;
        }
        
        private EntNetClient(ClientMain cm){
            clientMain = cm;
            k_db = "cornell";
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
        	System.out.println("client: k_session = "+k_session);
        	//SharedKey sk = SharedKey.getInstance();
        	//k_session = sk.generateSessionKeyWithPassword(sessionKeyGenStr);
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
        
        
        
        
        public void clientRegist(String uname, String pwd, String contactinfo, String roleID) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
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
        	//System.out.println("k_session="+k_session);
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
        
/*
        public void clientLogin_old(String tmp_uid, String tmp_pwd)
			throws IOException {
                        
				HashMap<String, String> loginCredential = new HashMap<String, String>();
				loginCredential.put("user_id", tmp_uid); 
				loginCredential.put("password", tmp_pwd); 
				clientRequest loginRequest = new clientRequest(
						Constants.LOGIN_REQUEST_ID, tmp_uid, loginCredential, this.k_db);
				XMLRequest xmlr= loginRequest.clientRequestLogin();
                //System.out.println("client session key: "+xmlr.sessionKey.skey);
                invokeRequestThread(xmlr);
        }
        
        public void establishSessionKey_old(){

            SecureRandom r = new SecureRandom();
            byte[] salt = new byte [8];
            //int s = r.nextInt()%100;
            for (int i=0;i<salt.length;i++){
                salt[i] = (byte) ((int)salt[i] + 256);
            }
            String result = null;
            for (int i = 0; i < salt.length; i++) {
            	result += Integer.toHexString((0x000000ff & salt[i]) | 0xffffff00).substring(6);
            }
            
            SharedKey sk = SharedKey.getInstance();
            
            this.sessionKey = sk.generateKeyWithPwd(result);
            XMLRequest.sessionKey = this.sessionKey;

            XMLRequest xmlreq = new XMLRequest(Constants.SESSION_KEY_EST,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.INVALID,
                    Constants.SESSION_KEY_EST,
                    Constants.INVALID);

            
            System.out.println("===sessionKey===");
            
            //TODO: encrypt XMLRequest.sessionKey with public key of server
            //but first need to get server's public key...
            //that means we need server to reply pub key back to client first...
            
            
            
            invokeRequestThread(xmlreq);
        }

        
        public void clientRegist_old(
                String Username,
                String Password,
                String ContactInfo,
                String Role_ID 
                        ) 
        {
			HashMap<String, String> RegistCredential = new HashMap<String, String>();
			try {
	                    RegistCredential.put("user_id", Username);
	                    RegistCredential.put("password", Password);
	                    RegistCredential.put("contact_info", ContactInfo);
	                    RegistCredential.put("role_id", Role_ID);
	        }catch (Exception e) {};
			clientRequest registRequest = new clientRequest(
					Constants.REGIST_REQUEST_ID, Username, RegistCredential, this.k_db);
			XMLRequest xmlr = registRequest.clientRequestRegist();
	        invokeRequestThread(xmlr);
        }
        
        */
        
        
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
        
        //static int index = 1; 
        
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
        	/*  
        	String sqlQueryString = "insert into friend values(\"" + friend_id + "\",\"" + thisUserID + 
							"\",\"" + messageString + "\",null);";
        	  */
        	  XMLRequest xmlRequest = new XMLRequest(
        			  Constants.UPDATE_REGION_ID, 
        			  thisUserID,
        			  Constants.REGION6, 
        			  Constants.INVALID, 
        			  Constants.POST_FRIEND_MESSAGE, 
        			  Constants.UPDATE);
        	  SharedKey sk = SharedKey.getInstance();
        	  xmlRequest.requestData.put("postedFriendID", 
          			  sk.sessionKeyEncrypt(this.k_session, friend_id));
        	  xmlRequest.requestData.put("postedFriendMessage", 
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
        	/*
        	HashMap<String, String> updateCredential = new HashMap<String, String>();
			updateCredential.put("regionID", regionID); 
			updateCredential.put("newContent", newContent); 
			clientRequest updateRegionRequest = new clientRequest(
					Constants.UPDATE_REGION_ID, this.thisUserID, updateCredential, this.k_db);
			XMLRequest xmlr= updateRegionRequest.clientRequestUpdateRegion(regionID, newContent, this.thisUserID);
	                invokeRequestThread(xmlr);*/
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
                    
                }catch(Exception e){};
            }
            else{
            	//login failed
            	if (commandline) {
					test.LoginCallBack(xmlreq);
				} else {
					System.out.println("requestThreadCallBack: login failed");
				}
                return;           
            }
        } //end of login request threadCallBack
                
        else if (xmlreq.getRequestID().equals(Constants.SESSION_KEY_EST)){
            String SessionKeyEstStat = xmlreq.getRequestDetail();
            if (SessionKeyEstStat==Constants.TRUE){
                XMLRequest.sessionKey = this.sessionKey;
                System.out.println("in requestThreadCallback, session key successfully established");
            }
            else{
            	System.out.println("in requestThreadCallback, failed to establish session key");
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
            	
            	//System.out.println("Constants.FRIENDLISTREGION in callback");
            	//System.out.println(xmlreq.getMyResultSet());
            	
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

                //this.clientMain.LoginToHome();
            }
            
            else if (regionID.equals(Constants.NOTIFYREGION)){
                for (int i=0;i<myRS.getTable().size();i++){
                	SharedKey sk = SharedKey.getInstance();
                    String friendUID = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "user1")));
                    resultSetArrayList.add(friendUID);
                	
                	//String friRequest = myRS.getStringValue(i, "user1");
                    //resultSetArrayList.add(friRequest);
                    //System.out.println("testing");
                    //System.out.println(friRequest);
                }
                if (commandline) {
					//test.personPanelCallback(Constants.FRIENDLISTREGION, resultSetArrayList);
				} else {
	                this.clientMain.giveArrayListToUI(resultSetArrayList, regionID);
	                //System.out.println("=======FRIENDNOTIFY TESTING======="+resultSetArrayList);
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
            	
                //String contact_info = myRS.getStringValue(0, "contact_info");
                //String uid = myRS.getStringValue(0,"user_id");
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
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
            }
            else if (regionID.equals(Constants.REGION3)){
            	SharedKey sk = SharedKey.getInstance();
            	String currProj = 
                	new String(sk.sessionKeyDecrypt(this.k_session, 
                			myRS.getCipherValue(0, "proj_name")));
            	
                //String currProj = myRS.getStringValue(0, "proj_name");
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
                	SharedKey sk = SharedKey.getInstance();
                    String msg_content = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "msg_content")));
                    //String msg_content = myRS.getStringValue(i, "msg_content");
                    resultSetArrayList.add(msg_content);
                    //System.out.println("company msg = "+msg_content);
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
                	SharedKey sk = SharedKey.getInstance();
                    String msg_content = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "msg_content")));
                    //String msg_content = myRS.getStringValue(i, "msg_content");
                    resultSetArrayList.add(msg_content);
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
                	SharedKey sk = SharedKey.getInstance();
                    String msg_content = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "message")));
                    String msg_sender = 
                    	new String(sk.sessionKeyDecrypt(this.k_session, 
                    			myRS.getCipherValue(i, "user2")));
                    
                    //String msg_content = myRS.getStringValue(i, "message");
                    resultSetArrayList.add(msg_content+" (FROM: "+msg_sender+")");
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
                System.out.println("update region successful");
            }
            else{
                System.err.println("ERROR: requestThreadCallBack cannot update region");
                return;
            }
            
        }
    }

    
}
