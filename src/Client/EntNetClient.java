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
        String fromServer;
        String fromUser;
        try{
            while ((fromServer = in.readLine()) != null) {
                System.out.println("SERVER MESSAGE: " + fromServer);
                if (fromServer.equals("BYE"))
                    break;                
                fromUser = stdIn.readLine();
                //System.out.println("Client: " + fromUser);
                out.println(fromUser);
            }
            out.close();
            in.close();
            stdIn.close();
            s.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
