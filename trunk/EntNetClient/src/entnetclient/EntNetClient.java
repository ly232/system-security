/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Lin
 */
public class EntNetClient { 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        //load client gui--login page
        
        //new UserLoginPage().main(args);
        
        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        //establish client side socket:
        try{
            s = new Socket("localhost", 8189); //"localhost" is the destination (i.e. server) ip address
            out = new PrintWriter(s.getOutputStream(), true); //true means autoflush
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer = "";
        String fromUser = ""; 
        System.out.println("enter '_REGIST_REQUEST' if request for registration; otherwise press ENTER");
        try{
            fromUser = stdIn.readLine();
        }catch(Exception e){};
                
        try{
            //client-initiated actions: (only request for registration)
            //this corresponds to clicking "regist" button on login page GUI
            if (fromUser.equals("_REGIST_REQUEST")){
                
            }
            //END OF CLIENT REGISTRATION REQUEST
            ///////////////
            
            //server-initiated actions:
            while ((fromServer = in.readLine()) != null) {
                if (fromServer.equals("_LOGIN_REQUEST")){
                    System.out.println("enter user id:");
                    String tmp_uid = stdIn.readLine();
                    System.out.println("enter password:");
                    String tmp_pwd = stdIn.readLine();
                    clientLogin(tmp_uid,tmp_pwd,out); 
                    continue;
                }
                else if (fromServer.equals("_REGIST_INFO_REQUEST")){
                    
                }

                //echo server's response...this is just for testing...remove this before submission!!
                System.out.println("SERVER MESSAGE: " + fromServer);
                if (fromServer.equals("SERVER EXIT"))
                    break;                
                fromUser = stdIn.readLine();
                out.println(fromUser);
            }
            out.close();
            in.close();
            stdIn.close();
            s.close();
        }
        catch(Exception e){
            //e.printStackTrace();
        }
    }
    
    private static void clientLogin(String uid, String pwd, PrintWriter out){
        //initial login credential: (TODO: this should correspond to GUI event in login page)
        ArrayList<String> loginCredential = new ArrayList<String>();
        loginCredential.add(uid); //username...TODO: implement in GUI
        loginCredential.add(pwd); //password...TODO: implement in GUI
        clientRequest loginRequest = new clientRequest(Constants.LOGIN_REQUEST_ID, loginCredential);
        String retXML = loginRequest.generateXMLforRequest(); 
        out.println(retXML);
        //System.out.println(retXML);
    }
    
}
