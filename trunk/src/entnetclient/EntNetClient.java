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

	static PrintWriter out = null;
	static BufferedReader in = null;

	public static void main(String[] args) {

		// load client gui--login page

		// new UserLoginPage().main(args);
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new view.LoginUI().setVisible(true);
			}
		});

		Socket s = null;

		// establish client side socket:
		try {
			s = new Socket("localhost", 8189); // "localhost" is the destination
												// (i.e. server) ip address
			out = new PrintWriter(s.getOutputStream(), true); // true means
																// autoflush
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        
                        //print out server welcome msg (at commandline only. not on gui.):
                        System.out.println(in.readLine());
                        
		} catch (Exception e) {
		}
		

	}
        
	public static void clientRegist(
                String VerificationCode, 
                String Username,
                String Password,
                String RealName,
                String ContactInfo,
                String Role_ID 
                        ) {
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
		String retXML = registRequest.generateXMLforRequest();
		out.println(retXML);
	}

	public static void clientLogin(String tmp_uid, String tmp_pwd)
			throws IOException {

            //System.out.println("in clientLogin()");
            
		// initial login credential: (TODO: this should correspond to GUI event
		// in login page)
		HashMap<String, String> loginCredential = new HashMap<String, String>();
		loginCredential.put("user_id", tmp_uid); 
		loginCredential.put("password", tmp_pwd); 
		clientRequest loginRequest = new clientRequest(
				Constants.LOGIN_REQUEST_ID, tmp_uid, loginCredential);
		String retXML = loginRequest.generateXMLforRequest();
		out.println(retXML);
		System.out.println(retXML);
		
                
                String XMLfromServer = in.readLine();
		
                
                System.out.println(XMLfromServer);
                
                
                
                //if (fromServer.equals("REGISTRATION_ACCEPTED"));
		displayUserHomeBoard();
		// CLOSE
	}

	private static void displayUserHomeBoard() {
		// TODO Auto-generated method stub

	}

    public ArrayList<String> fetchUidPwdFromGUI(String uid, String pwd){
        ArrayList<String> retArrList = new ArrayList<String>();
        
        return retArrList;
    }
    
}
