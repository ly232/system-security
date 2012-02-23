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
        
        //ThreadHandler private variables:
        private Socket incoming;
        private DataBase sysDB;
        private final int MAX_LOGIN_TRAIL = 5;
        private String user_id = ""; //each thread serves a single user only. we need to keep track of who the thread is serving
        private String VERIFICATION_CODE = "cornell"; //used for user registration
        
        public ThreadedHandler(Socket i, DataBase db){
            incoming = i;
            sysDB = db;
        }
        
        public void run(){
                try{
                    InputStream inStream = incoming.getInputStream();
                    OutputStream outStream = incoming.getOutputStream();
                    Scanner in = new Scanner(inStream);
                    PrintWriter out = new PrintWriter(outStream, true); //true means autoflush
                    
                    out.println("===WELCOME TO THE LOGIN/REGISTRATION PAGE===");
                    out.println("please enter 'login' or 'register'");
                    
                    XML_parser_API login_regist_xml = new XML_parser_API(in.nextLine());
                    if (login_regist_xml.getRootTagName().equals("client_login_request")){
                        if (!processLogin(in, out, login_regist_xml)){ //incorrect login for MAX_LOGIN_TRAIL trails
                            out.println("FORCE CLIENT SHUTDOWN");
                            incoming.close();
                        }
                    }
                    else if (login_regist_xml.getRootTagName().equals("client_regist_request")){
                        //System.out.println("REGISTRATION REQ RECEIVED");
                        
                        
                        while(true){
                            if (login_regist_xml.getTagValue("ver_code").equals(VERIFICATION_CODE)){ //TODO: verification code is hardcoded now...modify this later (maybe phase 3?)
                                while(true){
                                    if (processRegist(in, out, login_regist_xml)){
                                       out.println("REGISTRATION_ACCEPTED"); 
                                       break;
                                    }
                                    else{
                                       out.println("username is already used. please use another username."); 
                                       XML_parser_API tmp_xml_parser = new XML_parser_API(in.nextLine());
                                       login_regist_xml = tmp_xml_parser;
                                    }
                                }
                                break;
                            }
                            else{
                                out.println("incorrect verification code. please register with a correct verification code.");
                                XML_parser_API tmp_xml_parser = new XML_parser_API(in.nextLine());
                                login_regist_xml = tmp_xml_parser;
                            }
                        }
                        
                    }
                    else{
                        System.out.println("ERROR: cannot pass the login/regist page...something went wrong");
                    }
                         
                        out.println("welcome, "+user_id+". please enter your message to be echoed. enter \"exit\" to exit.");
                        while(in.hasNextLine()){
                            String line = in.nextLine();
                            if (line.trim().toLowerCase().equals("exit")){
                                //done = true;
                                out.println("BYE"); //signal the client to exit
                                incoming.close();
                                break;
                            }
                            out.println("Echo: "+line);
                        }

                }catch(Exception e){};
        }
        
        
        //for register:
        private boolean processRegist(Scanner in, PrintWriter out, XML_parser_API login_regist_xml){
            HashMap<String,String> registCred = login_regist_xml.XML2Table();
            String myQuery = "INSERT INTO user VALUES ('" + registCred.get("user_id") 
                    + "', '" + registCred.get("password") 
                    + "', '" + registCred.get("person_name")
                    + "', '" + registCred.get("contact_info")
                    + "', '" + registCred.get("role_id") + "')";
            try{
                //System.out.println("update query = "+myQuery);
                
                int retStat = sysDB.DoUpdateQuery(myQuery);
                if (retStat==1)
                    return true;
                else
                    return false;
            }
            catch(Exception e){
                System.out.println("Database error occured during ThreadHandler::processRegist(). Most likely an insertion failure.");
                return false;
            }
            
        }
        
        //for login:
        private boolean processLogin(Scanner in, PrintWriter out, XML_parser_API login_regist_xml){
            int numTrailFailed = 0;
            
            while (true){
                if (numTrailFailed==MAX_LOGIN_TRAIL)
                    return false; //failed login--too many trails
                HashMap<String,String> loginCred = login_regist_xml.XML2Table();
                this.user_id = loginCred.get("user_id");
                String password = loginCred.get("password");
  
                
                if (!validateUser( user_id,password)){
                    numTrailFailed++;
                    out.println("invalid login credentials. you have "+(MAX_LOGIN_TRAIL-numTrailFailed)+" more attempts.");
                    XML_parser_API tmp_xml_parser = new XML_parser_API(in.nextLine());
                    login_regist_xml = tmp_xml_parser;
                }
                else{
                    //out.println("LOGIN_ACCEPTED");
                    //TODO: instead of sending client "LOGIN_ACCEPTED", send client the xml to display his homepage
                    
                    String userHomeBoardXMLstring = getUserHomeBoardXMLstring();
                    
                    
                    
                    return true;
                }
            }
        }
        //for login:
        private String getUserHomeBoardXMLstring(){
            String userHomeBoardXMLstring = "";
            
            XML_creator_API userHomeBoardXML = new XML_creator_API();
            userHomeBoardXML.createRoot("user_home_board");
            userHomeBoardXML.createChild("user_list", "user_home_board");
            userHomeBoardXML.createChild("user_post_board", "user_home_board");
            
            //probe 
            
            return userHomeBoardXMLstring;
            
        }
        //for login:
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

    }