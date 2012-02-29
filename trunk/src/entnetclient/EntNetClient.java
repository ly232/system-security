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
import com.sun.rowset.WebRowSetImpl;
import java.nio.CharBuffer;
import javax.sql.rowset.WebRowSet;
import view.*;
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
        private boolean commandline = true;
        
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
        
        
        
        ///////////////////////////////////
        
        
        public void clientRegist(
                String VerificationCode, 
                String Username,
                String Password,
                String RealName,
                String ContactInfo,
                String Role_ID 
                        ) 
        {
		HashMap<String, String> RegistCredential = new HashMap<String, String>();
		try {
                    RegistCredential.put("ver_code", VerificationCode);
                    RegistCredential.put("user_id", Username);
                    RegistCredential.put("password", Password);
                    RegistCredential.put("person_name", RealName);
                    RegistCredential.put("contact_info", ContactInfo);
                    RegistCredential.put("role_id", Role_ID);
                } catch (Exception e) {
		}
		;

		clientRequest registRequest = new clientRequest(
				Constants.REGIST_REQUEST_ID, Username, RegistCredential);
		//String retXML = registRequest.generateXMLforRequest();
		XMLRequest xmlr = registRequest.clientRequestRegist();
                        
                invokeRequestThread(xmlr);
	}
        
        public void quitClient(){
        	XMLRequest xmlRequest = new XMLRequest(Constants.QUIT_ID, 
        			  thisUserID,Constants.FRIENDLISTREGION, null, Constants.QUIT_ID, Constants.UPDATE);
        	  invokeRequestThread(xmlRequest);
                  //System.exit(0);
                  this.clientMain.quit();
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
        	  String sqlQueryString = "insert into friend values(\"" + thisUserID + "\",\"" + friend_id + 
							"\",\"" + messageString + "\",null);";
        	  XMLRequest xmlRequest = new XMLRequest(Constants.UPDATE_REGION_ID, 
        			  thisUserID,Constants.FRIENDLISTREGION, null, sqlQueryString, Constants.UPDATE);
        	  invokeRequestThread(xmlRequest);
        }
        
        public void getFriendList(){
        	  String sqlQueryString = null;//"insert into friend values("tao","lin","hello",null);";
        	  XMLRequest xmlRequest = new XMLRequest(Constants.READ_REGION_ID, 
        			  thisUserID,Constants.FRIENDLISTREGION, null, sqlQueryString, Constants.SELECT);
          	  invokeRequestThread(xmlRequest);
        }
        
        
        public void clientLogin(String tmp_uid, String tmp_pwd)
			throws IOException {
                        
		HashMap<String, String> loginCredential = new HashMap<String, String>();
		loginCredential.put("user_id", tmp_uid); 
		loginCredential.put("password", tmp_pwd); 
		clientRequest loginRequest = new clientRequest(
				Constants.LOGIN_REQUEST_ID, tmp_uid, loginCredential);
		XMLRequest xmlr= loginRequest.clientRequestLogin();
                invokeRequestThread(xmlr);

	}
        
        
        /*
        public void  clientViewOtherPersonBoard(String otherPersonUid){
                //kill the current view and goto another person's board...maybe not?
            
                HashMap<String, String> otherPersonBoardInfo = new HashMap<String, String>();
		otherPersonBoardInfo.put("user_id", otherPersonUid); 
		clientRequest viewOtherPersonBoardRequest = new clientRequest(
				Constants.READ_REGION_ID, otherPersonUid, otherPersonBoardInfo);
		ArrayList<XMLRequest> arr_xmlr= viewOtherPersonBoardRequest.clientRequestOtherPersonBoard(otherPersonUid);
                //arr_xmlr contains an array of xml requests to read other person's board
                //need to send each request to server ==> multiple threads
                
                for (int i=0;i<arr_xmlr.size();i++){
                    invokeRequestThread(arr_xmlr.get(i));
                }     
        }*/
        
        public void  clientViewOtherPersonBoard(String otherPersonUid, String switchBoardCode) throws IOException{
            
            //general logic: close current MainUI, then open a new MainUI populated based on the retXML
            
            ArrayList<XMLRequest> otherPersonBoardInfoXML = clientHomeBoardRequest(otherPersonUid);
            
            //close the current ui, then open a new ui
            //there are 3 possible senarios to switch:
            //1. my home board -> other person's home board
            //2. other person's home board -> other person's home board
            //3. other person's home board -> my home board

            if (otherPersonUid.equals(Constants.HOME_TO_OTHER_VIEW)){
                clientMain.HomeToPerson();
            }
            else if (otherPersonUid.equals(Constants.OTHER_TO_OTHER_VIEW)){
                clientMain.PersonToPerson();
            }
            else if (otherPersonUid.equals(Constants.OTHER_TO_HOME_VIEW)){
                clientMain.PersonToHome();
            }
            else {
                System.err.println("ERROR: invalid switchBoardCode in EntNetClient::clientViewOtherPersonBoard");
                return;
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
				Constants.UPDATE_REGION_ID, this.thisUserID, updateCredential);
		XMLRequest xmlr= updateRegionRequest.clientRequestUpdateRegion(regionID, newContent, this.thisUserID);
                invokeRequestThread(xmlr);
        }
        
        
        
        private ArrayList<XMLRequest> clientHomeBoardRequest(String uid)
                throws IOException {
            HashMap<String, String> homeBoardRequestInfo = new HashMap<String, String>();
            //homeBoardRequestInfo.put("user_id",uid);
            clientRequest homeBoardRequest = new clientRequest(
				Constants.READ_REGION_ID, uid, homeBoardRequestInfo); 
            ArrayList<XMLRequest> al_xmlr= homeBoardRequest.clientRequestHomeBoard();
            return al_xmlr;
        }
        
        public void returnUserHomePage() throws IOException{
                ArrayList<XMLRequest> xmlreq = clientHomeBoardRequest(this.thisUserID);
                for (int i=0;i<xmlreq.size();i++){
                        invokeRequestThread(xmlreq.get(i));
                    }
                if (commandline) {
					
				} else {
	                this.clientMain.PersonToHome();
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

                    
                    //populate screen: close loginUI, open new UI
                    if (commandline) {
						
					}else {
	                    this.clientMain.LoginToHome();
					}


                    
                    
                }catch(IOException e){};
            }
            else{
                     return;           
            }
        } //end of login request threadCallBack
                
        else if (xmlreq.getRequestID().equals(Constants.REGIST_REQUEST_ID)){
            //check if the login is successful
            String rowAffected = xmlreq.getRequestDetail();
            if (rowAffected.equals("1")){
                //successful registration
                //goto user's home page by asking server to send xml of user homeboard
                try{
                    ArrayList<XMLRequest> homeBoardInfoXML 
                            = clientHomeBoardRequest(xmlreq.getUserID());
                    //populate screen: close loginUI, open new UI
                    this.clientMain.LoginToHome();
                    
                    //now we are in homepage...load home board content
                    for (int i=0;i<homeBoardInfoXML.size();i++){
                        invokeRequestThread(homeBoardInfoXML.get(i));
                    }
                    
                }
                catch(IOException e){
                };
            }
            else{
                //failed registration
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
            else if (regionID.equals(Constants.REGION1)){
                String contact_info = myRS.getStringValue(0, "contact_info");
                resultSetArrayList.add(contact_info);
                //resultSetHashMap.put("contact_info", contact_info);
                if (commandline) {
					//test.personPanelCallback(Constants.REGION1, resultSetArrayList);
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
					//test.personPanelCallback(Constants.REGION2, resultSetArrayList);
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
                    String msg_id = myRS.getStringValue(i, "msg_id"); //msg_id, msg_content
                    String msg_content = myRS.getStringValue(i, "msg_content");
                    resultSetArrayList.add(msg_content);
                    //resultSetHashMap.put(msg_id, msg_content);
                }
                //TODO: send resultSetHashMap to GUI--waiting for shuai's api
                if (commandline) {
					//test.personPanelCallback(Constants.REGION4, resultSetArrayList);
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
