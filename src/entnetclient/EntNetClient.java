/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

//import XML.Constants;
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
import view.LoginUI;
import view.MainUI;
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
        private EntNetClient(ClientMain cm){
            clientMain = cm;
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
        
        public void clientViewOtherBoard(){
            
        }
        
        public void clientPostFriendMessage(String uIDsrc, String uIDdest, String friendMsg){
            
        }
        
        
        public void clientUpdateRegion(String regionID, String newContent, String uIDsrc){
                HashMap<String, String> updateCredential = new HashMap<String, String>();
		updateCredential.put("regionID", regionID); 
		updateCredential.put("newContent", newContent); 
		clientRequest updateRegionRequest = new clientRequest(
				Constants.UPDATE_REGION_ID, uIDsrc, updateCredential);
		XMLRequest xmlr= updateRegionRequest.clientRequestUpdateRegion(regionID, newContent, uIDsrc);
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
    

    private void invokeRequestThread(XMLRequest xmlr) {
        requestHandler rh = new requestHandler(xmlr, this);
        Thread t = new Thread(rh);
        t.start();
    }
    
    public void requestThreadCallBack(XMLRequest xmlreq) throws SQLException{
        if (xmlreq.getRequestID().equals(Constants.LOGIN_REQUEST_ID)){
            //check if the login is successful
            String LoginStat = xmlreq.getRequestDetail();
            if (LoginStat.equals(Constants.TRUE)){
                //successful login
                //goto user's home page by asking server to send xml of user homeboard
                try{
                    ArrayList<XMLRequest> homeBoardInfoXML 
                            = clientHomeBoardRequest(xmlreq.getUserID());
                    
                    //populate screen: close loginUI, open new UI
                    this.clientMain.killLoginUI();
                    
                    
                    //now we are in homepage...load home board content
                    for (int i=0;i<homeBoardInfoXML.size();i++){
                        invokeRequestThread(homeBoardInfoXML.get(i));
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
                    this.clientMain.killLoginUI();
                    
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
            System.out.println("inside callback readRegionRequest");
            System.out.println(xmlreq.getRequestDetail());
            
            //TODO: send to UI (to clientMain) a hashmap containing:
            // 1. region ID
            // 2. region content
            HashMap<String, String> resultSetHashMap = new HashMap<String, String>();
            String regionID = xmlreq.getMyResultSeStringt();
            MyResultSet myRS;
            if (xmlreq.getRequestDetail().equals(Constants.RETURN_RESULTSET))
                myRS = xmlreq.getMyResultSet();
            else
                System.err.println("ERROR: entnetclient callback readregion did not return myresultset");
            if (regionID.equals(Constants.FRIENDLISTREGION)){
                
            }
            else if (regionID.equals(Constants.REGION1)){
                
            }
            else if (regionID.equals(Constants.REGION2)){
                
            }
            else if (regionID.equals(Constants.REGION3)){
                
            }
            else if (regionID.equals(Constants.REGION4)){
                
            }
            else if (regionID.equals(Constants.REGION5)){
                
            }
            else if (regionID.equals(Constants.REGION6)){
                
            }
            else{
                System.err.println("requestThreadCallBack ERROR: cannot identify region id");
                return;
            }

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
