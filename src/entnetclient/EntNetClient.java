/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import XML.Constants;
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
		} catch (Exception e) {
		}
		

	}

	public static void clientRegist(BufferedReader stdIn, PrintWriter out) {
		HashMap<String, String> RegistCredential = new HashMap<String, String>();
		try {
			System.out.println("enter your verification code:"); //verification code is "cornell"
			RegistCredential.put("ver_code", stdIn.readLine());
			System.out.println("sign-up user id:");
			RegistCredential.put("user_id", stdIn.readLine());
			System.out.println("enter your password:");
			RegistCredential.put("password", stdIn.readLine());
			System.out.println("enter your name:");
			RegistCredential.put("person_name", stdIn.readLine());
			System.out.println("enter your contact info:");
			RegistCredential.put("contact_info", stdIn.readLine());
			System.out.println("enter your role: 1 for boss, 2 for department head, 3 for regular employee"); // TODO:
																				// GUI
			RegistCredential.put("role_id", stdIn.readLine());
		} catch (Exception e) {
		}
		;

		clientRequest registRequest = new clientRequest(
				Constants.REGIST_REQUEST_ID, RegistCredential);
		String retXML = registRequest.generateXMLforRequest();
		out.println(retXML);
	}

	public static void clientLogin(String tmp_uid, String tmp_pwd)
			throws IOException {

		// initial login credential: (TODO: this should correspond to GUI event
		// in login page)
		HashMap<String, String> loginCredential = new HashMap<String, String>();
		loginCredential.put("user_id", tmp_uid); // username...TODO: implement
													// in GUI
		loginCredential.put("password", tmp_pwd); // password...TODO: implement
													// in GUI
		clientRequest loginRequest = new clientRequest(
				Constants.LOGIN_REQUEST_ID, loginCredential);
		String retXML = loginRequest.generateXMLforRequest();
		out.println(retXML);
		// System.out.println(retXML);
		String fromServer = in.readLine();
		if (fromServer.equals("REGISTRATION_ACCEPTED"))
			;
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
