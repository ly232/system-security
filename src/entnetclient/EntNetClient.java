/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

//import XML.Constants;
import java.io.*;
import java.net.*;
import java.util.*;
import Constants.*;
import XML.XMLRequest;
import java.nio.CharBuffer;
import view.LoginUI;
/**
 * 
 * @author Lin
 */


public class EntNetClient {

	/**
	 * @param args
	 *            the command line arguments
	 */

	private PrintWriter out = null;
	private BufferedReader in = null;

        //for singleton controller
        private static EntNetClient instance;
        private Socket s;
        private Thread clientMainThread;        
        
        private EntNetClient(){
            // establish client side socket:
		try {
                    /*
			s = new Socket("localhost", 8189); 
			out = new PrintWriter(s.getOutputStream(), true); // true means																// autoflush
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        //print out server welcome msg (at commandline only. not on gui.):
                        System.out.println(in.readLine());
                        Runnable r = 
                                new ClientMainThreadHandler();
                        clientMainThread = new Thread(r);
                        clientMainThread.start();  */      
		} 
                catch (Exception e) {
		}
        }   

        public static EntNetClient getInstance() {
            if (instance==null)
                return new EntNetClient();
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
        
        public ArrayList<XMLRequest> clientHomeBoardRequest(String uid)
                throws IOException {
            HashMap<String, String> homeBoardRequestInfo = new HashMap<String, String>();
            //homeBoardRequestInfo.put("user_id",uid);
            clientRequest homeBoardRequest = new clientRequest(
				Constants.READ_REGION_ID, uid, homeBoardRequestInfo); 
            ArrayList<XMLRequest> al_xmlr= homeBoardRequest.clientRequestHomeBoard();
            return al_xmlr;
        }
        
	

    
/*
    public ArrayList<String> fetchUidPwdFromGUI(String uid, String pwd){
        ArrayList<String> retArrList = new ArrayList<String>();
        
        return retArrList;
    }*/

    private void invokeRequestThread(XMLRequest xmlr) {
        requestHandler rh = new requestHandler(xmlr, this);
        Thread t = new Thread(rh);
        t.start();
    }
    
    public void requestThreadCallBack(XMLRequest xmlreq){
        if (xmlreq.getRequestID().equals(Constants.LOGIN_REQUEST_ID)){
            //check if the login is successful
            String LoginStat = xmlreq.getRequestDetail();
            if (LoginStat.equals(Constants.TRUE)){
                //successful login
                //goto user's home page by asking server to send xml of user homeboard
                try{
                    ArrayList<XMLRequest> homeBoardInfoXML 
                            = clientHomeBoardRequest(xmlreq.getUserID());
                    //populate screen
                }catch(IOException e){};
            }
            else{
                //failed login...TODO: solution?
            }
            
            
        }
        else if (xmlreq.getRequestID().equals(Constants.REGIST_REQUEST_ID)){
            
        }
            
    }
    
    
}
