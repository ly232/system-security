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
        catch(Exception e){};
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer = "";
        String fromUser = "";
                
        try{
            //TODO: put these in GUI login/regist page.
            String serverWelcomePrompt = in.readLine();
            String serverLoginRegistPagePrompt = in.readLine();
            System.out.println(serverWelcomePrompt); 
            System.out.println(serverLoginRegistPagePrompt);
            while (true){
                try{
                    fromUser = stdIn.readLine();
                    if (fromUser.equals("register")){     
                        clientRegist(stdIn,out); 
                        while (true){
                            fromServer = in.readLine();
                            if (fromServer.equals("REGISTRATION_ACCEPTED"))
                                break; //TODO: bring user to his homepage board
                            else{
                                System.out.println(fromServer);
                                clientRegist(stdIn,out); 
                            }
                        }
                        
                        break;
                    }
                    else if (fromUser.equals("login")){
                        
                        clientLogin(stdIn,out); 
                        
                        while (true){
                            fromServer = in.readLine();
                            if (fromServer.equals("LOGIN_ACCEPTED"))
                                break; //TODO: bring user to his homepage board
                            else if (fromServer.equals("FORCE CLIENT SHUTDOWN")){
                                System.out.println("Too many failed login attempts. Client is forced to shut down by the server.");
                                System.exit(1);
                            }
                            System.out.println(fromServer);
                            clientLogin(stdIn,out); 
                        }
                        break;
                    }
                    else{
                        System.out.println("invalid login/regist page request. please enter 'login' or 'register'.");
                    }
                }catch(Exception e){};
            }
            //server-initiated actions:
            while ((fromServer = in.readLine()) != null) {
      
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
    private static void clientRegist(BufferedReader stdIn, PrintWriter out){ 
        //ArrayList<String> RegistCredential = new ArrayList<String>();
        HashMap<String,String> RegistCredential = new HashMap<String,String>();
        try{
            System.out.println("enter your verification code:"); //for now, verification code is hard-coded at the server to be "cornell". ver code is NOT stored in db@server.
            //RegistCredential.add(stdIn.readLine());              
            RegistCredential.put("ver_code", stdIn.readLine());
            System.out.println("sign-up user id:");
            RegistCredential.put("user_id",stdIn.readLine());
            System.out.println("enter your password:");
            RegistCredential.put("password",stdIn.readLine());
            System.out.println("enter your name:");
            RegistCredential.put("person_name",stdIn.readLine());
            System.out.println("enter your contact info:");
            RegistCredential.put("contact_info",stdIn.readLine());
            System.out.println("enter your role: 1 for boss, 2 for department head, 3 for regular employee"); //TODO: Chen--add error checking here when you do GUI
            RegistCredential.put("rold_id",stdIn.readLine());
        }catch(Exception e){};
        
        clientRequest registRequest = new clientRequest(Constants.REGIST_REQUEST_ID, RegistCredential);
        String retXML = registRequest.generateXMLforRequest();
        System.out.println(retXML);
    }
    private static void clientLogin(BufferedReader stdIn, PrintWriter out){
        String tmp_uid = "";
        String tmp_pwd = "";
        try{
            System.out.println("enter user id:");
            tmp_uid = stdIn.readLine();
            System.out.println("enter password:");
            tmp_pwd = stdIn.readLine();
        }catch(Exception e){};
        //initial login credential: (TODO: this should correspond to GUI event in login page)
        HashMap<String,String> loginCredential = new HashMap<String,String>();
        loginCredential.put("user_id",tmp_uid); //username...TODO: implement in GUI
        loginCredential.put("password",tmp_pwd); //password...TODO: implement in GUI
        clientRequest loginRequest = new clientRequest(Constants.LOGIN_REQUEST_ID, loginCredential);
        String retXML = loginRequest.generateXMLforRequest(); 
        out.println(retXML);
        //System.out.println(retXML);
    }
    
}
