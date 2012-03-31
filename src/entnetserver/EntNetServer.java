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
import java.sql.*;

/**
 *
 * @author Lin
 */
public class EntNetServer {

	public static String pwd;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            
            DataBase sysDB = new DataBase("jdbc:mysql://localhost:3306/entnetdb_v3",
                                            "root",
                                            "");
            sysDB.initialize();
            
  		  InputStreamReader stdin = new  InputStreamReader(System.in);
		   BufferedReader bReader = new BufferedReader(stdin);
		   System.out.println("Please input the DataAdministration PWD");
		    pwd = bReader.readLine();

		  
            
            
            /*--------------------
            //DB test:
            ResultSet rs = sysDB.DoQuery("SELECT * FROM Users");
            while(rs.next()){ //must know table schema to extract results
                String uid = rs.getString("username");
                String pwd = rs.getString("password");
                int roleID = rs.getInt("roleID");
                int did = rs.getInt("did");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String location = rs.getString("location");
                String currProj = rs.getString("currProj");
                System.out.println("uid="+uid+", pwd="+pwd+", roleID"+roleID+", did="+did+", name="+name+", phone="+phone+", location="+location+", currProj="+currProj);
            }
            //-----------------*/

            
            
            
            System.out.println("SERVER STARTED. SERVER'S LOCALHOST IP = " + InetAddress.getLocalHost());
            
            int i=1;
            ServerSocket s = new ServerSocket(8189);
            //ServerSocket sessionSocket = new ServerSocket(12345);
            while(true){
                Socket incoming = s.accept();
                System.out.println("Spawning thread "+i);
                Runnable r = new ThreadedHandler(incoming, sysDB);
                Thread t = new Thread(r);
                t.start();
                i++;
            }  
        }catch(Exception e){}
    }

}
