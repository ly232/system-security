/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetserver;

import java.io.*;
import java.net.*;
import java.util.*;

import JDBC.DataBase;

import com.mysql.jdbc.Driver;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.*;
import Security.*;

/**
 *
 * @author Lin
 */
public class EntNetServer {

	public static String pwd;
    /**
     * @param args the command line arguments
     */
    static private SharedKey symmKeyCryptoAPI;
    static private String db_pwd;
    
    static private PublicKey serverPublicKey;
    static private PrivateKey serverPrivateKey;
    
    public static void main(String[] args) {

        
        //static SharedKey k_db 
        try{
            db_pwd = args[0];
            
            System.out.println("db_pwd="+db_pwd);

            DataBase sysDB = new DataBase("jdbc:mysql://localhost:3306/entnetdb_v3",
                                            "root",
                                            "mysql");
            sysDB.initialize();
            

            
            
            MyPKI mypki = MyPKI.getInstance();
        	MyKey mk = mypki.InitAsymmKeyPair();
        	serverPublicKey = mk.pubKey;
        	serverPrivateKey = mk.privKey;
        	
            
  		  InputStreamReader stdin = new  InputStreamReader(System.in);
		   BufferedReader bReader = new BufferedReader(stdin);

		   pwd = "cs5300cornell";//bReader.readLine();
                   
            
            System.out.println("SERVER STARTED. SERVER'S LOCALHOST IP = " + InetAddress.getLocalHost());
            
            int i=1;
            ServerSocket s = new ServerSocket(8189);
            //ServerSocket sessionSocket = new ServerSocket(12345);
            while(true){
                Socket incoming = s.accept();
                System.out.println("Spawning thread "+i);
                Runnable r = new ThreadedHandler(incoming, sysDB, db_pwd, serverPrivateKey, serverPublicKey);
                Thread t = new Thread(r);
                t.start();
                i++;
            }  
        }catch(Exception e){}
    }

}
