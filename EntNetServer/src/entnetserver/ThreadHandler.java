/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetserver;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import org.xml.sax.InputSource;
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
                    
                    //for login:
                    if (!processLogin(in, out)){ //incorrect login for MAX_LOGIN_TRAIL trails
                        out.println("TOO MANY FAILED LOGIN TRAILS. SERVER CLOSES CONNECTION.");
                        out.println("SERVER EXIT.");
                    }
                    else{
                        //now user has been authenticated...
                        out.println("welcome, "+user_id+". please enter your message to be echoed. enter \"exit\" to exit.");
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
                }
                finally{
                    incoming.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
        private ArrayList<String> fetchClientLoginCredential(Scanner in){
            ArrayList<String> loginCred = new ArrayList<String>();
            String uid = "";
            String pwd = "";
            try{
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new InputSource(new StringReader(in.nextLine())));
                doc.getDocumentElement().normalize();
                uid = getTagValue("user_id", doc.getDocumentElement());
                pwd = getTagValue("password", doc.getDocumentElement());
                //System.out.println("uid="+user_id);
                //System.out.println("pwd="+password);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            loginCred.add(uid);
            loginCred.add(pwd);
            return loginCred;
        }
        
        private boolean processLogin(Scanner in, PrintWriter out){
            
            int numTrailFailed = 0;
            while (true){
                if (numTrailFailed==MAX_LOGIN_TRAIL)
                    return false; //failed login--too many trails
                out.println("_LOGIN_REQUEST"); //notify the client to supply uid and pwd
                ArrayList<String> loginCred = fetchClientLoginCredential(in);
                this.user_id = loginCred.get(0);
                String password = loginCred.get(1);
                if (!validateUser(user_id,password))
                    numTrailFailed++;
                else
                    return true;
            }
            

        }
        
        private boolean validateUser(String username, String password){
            String myQuery = "SELECT * FROM user WHERE user_id='"+username+"' AND user_pwd='"+password+"'";
            //System.out.println(myQuery);
            ResultSet rs = sysDB.DoQuery(myQuery);
            
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
        }
        
        private static String getTagValue(String sTag, Element eElement) {
            NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
            Node nValue = (Node) nlList.item(0);
            return nValue.getNodeValue();
        }
        
        private Socket incoming;
        private DataBase sysDB;
        private final int MAX_LOGIN_TRAIL = 5;
        private String user_id = ""; //each thread serves a single user only. we need to keep track of who the thread is serving
    }