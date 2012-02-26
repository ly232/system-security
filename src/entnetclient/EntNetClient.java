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
			s = new Socket("localhost", 8189); 
			out = new PrintWriter(s.getOutputStream(), true); // true means																// autoflush
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        //print out server welcome msg (at commandline only. not on gui.):
                        System.out.println(in.readLine());
                        Runnable r = 
                                new ClientMainThreadHandler();
                        clientMainThread = new Thread(r);
                        clientMainThread.start();        
		} catch (Exception e) {
		}
        }   
        public Thread getClientMainThreadHandler(){
            return clientMainThread;
        }
        public static EntNetClient getInstance() {
            if (instance==null)
                return new EntNetClient();
            else
                return instance;
        }
        
        
        
        ///////////////////////////////////
        
        
        
        
	

	

    

    public ArrayList<String> fetchUidPwdFromGUI(String uid, String pwd){
        ArrayList<String> retArrList = new ArrayList<String>();
        
        return retArrList;
    }
    
}
