package Server;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Lin
 */
    class ThreadedEchoHandler implements Runnable{
        public ThreadedEchoHandler(Socket i){
            incoming = i;
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
                    //TODO: decrypt password--for phase 3
                    //TODO: access to database to authenticate users--for phase 2
                    while(!validUser(username,password)){
                        out.println("incorrect username/password. re-enter username:");
                        username = in.nextLine();
                        out.println("re-enter password:");
                        password = in.nextLine();
                    }                  
                    
                    out.println("welcome, "+username+". please enter your message to be echoed. enter \"BYE\" to exit.");
                    
                    boolean done = false;
                    while(!done && in.hasNextLine()){
                        String line = in.nextLine();
                        out.println("Echo: "+line);
                        if (line.trim().equals("BYE")) 
                            done = true;
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
            if (username.equals("ly232")&&password.equals("cornell"))
                return true;
            else
                return false;
        }
        
        private Socket incoming;
    }