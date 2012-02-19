/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetserver;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
/**
 *
 * @author Lin
 */
    class ThreadedHandler implements Runnable{
        public ThreadedHandler(Socket i, DataBase db){
            incoming = i;
            sysDB = db;
        }
        
        public void run(){
            try{
                try{
                    InputStream inStream = incoming.getInputStream();
                    OutputStream outStream = incoming.getOutputStream();
                    Scanner in = new Scanner(inStream);
                    PrintWriter out = new PrintWriter(outStream, true); //true means autoflush
                    out.println("hello. enter username:");
                    String username = in.nextLine();
                    out.println("enter password:");
                    String password = in.nextLine();
                    //Authentication:
                    while(!validUser(username,password)){
                        out.println("incorrect username/password. re-enter username:");
                        username = in.nextLine();
                        out.println("re-enter password:");
                        password = in.nextLine();
                    }                  
                    
                    out.println("welcome, "+username+". please enter your message to be echoed. enter \"exit\" to exit.");
                    
                    //boolean done = false;
                    while(in.hasNextLine()){
                        String line = in.nextLine();
                        if (line.trim().toLowerCase().equals("exit")){
                            //done = true;
                            out.println("BYE"); //signal the client to exit
                            break;
                        }
                        out.println("Echo: "+line);
                    }
                    
                }
                finally{
                    incoming.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
        private boolean validUser(String username, String password){
            ResultSet rs = sysDB.DoQuery("SELECT * FROM Users WHERE username='"+username+"' AND password='"+password+"'");
            boolean validUser = false;
            try{
                validUser = rs.first();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                return validUser;
            }
            
            
            /*
            if (username.equals("ly232")&&password.equals("cornell"))
                return true;
            else
                return false;*/
        }
        
        private Socket incoming;
        private DataBase sysDB;
    }