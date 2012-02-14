/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import java.io.*;
import java.net.*;
import java.util.*;
//database stuff:
import com.mysql.jdbc.Driver;
import java.sql.*;

/**
 *
 * @author Lin
 */
public class EchoServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{


            //get IP address for localhost
            //InetAddress localHostAddress = InetAddress.getLocalHost();
            //System.out.println("server: local host IP = " + localHostAddress);
            System.out.println("Server Started...");
            int i=1;
            ServerSocket s = new ServerSocket(8189);
            while(true){
                Socket incoming = s.accept();
                System.out.println("Spawning thread "+i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
                
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
