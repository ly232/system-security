/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetserver;


import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

import org.omg.CORBA.INITIALIZE;
import Constants.*;
import XML.*;

import Constants.Constants;
import JDBC.DataBase;
/**
 *
 * @author Lin
 */
    class ThreadedHandler implements Runnable{
        
    	
        /**
         * @param i Socket with client
         * @param db the mysql database
         */
        public ThreadedHandler(Socket i, DataBase db){
            incoming = i;
            sysDB = db;
        }
    	
    	
        //ThreadHandler private variables:
        private Socket incoming;
        private final int MAX_LOGIN_TRAIL = 5;
        private String user_id = ""; //each thread serves a single user only. we need to keep track of who the thread is serving
        private String VERIFICATION_CODE = "cornell"; //used for user registration
        private Scanner read;
        private PrintWriter write;
        private InputStream inStream;
        private OutputStream outStream;
        private int threadCount = 0;

        public OutputStream getOutStream() {
			return outStream;
		}

		public void setOutStream(OutputStream outStream) {
			this.outStream = outStream;
		}

        public DataBase getSysDB() {
			return sysDB;
		}

		public void setSysDB(DataBase sysDB) {
			this.sysDB = sysDB;
		}
		private DataBase sysDB;

		
		
		/**
         * @param no
         * @return void
         * @throws IOException from getInputStream();
         */
        private void initializeServer() throws IOException{
        	
        		inStream = incoming.getInputStream();
        	    outStream = incoming.getOutputStream();
        		read = new Scanner(inStream);
        		write = new PrintWriter(outStream, true); //true means autoflush
        		write.println("===Connect with the server===");
        }
        

                
        /**
         * @param result threads callback the result
         * @return the output of the SQLQuery, the String will forget afterwards.
         * @throws IOException 
         */
        public void callBackResult(XMLRequest rq) throws IOException {
				write.print(rq.generateXMLRequest());
				System.out.println(rq.generateXMLRequest());
		}
        
        public void run(){
                try{
                	
                	initializeServer();
                	
                    //write.println("please enter 'login' or 'register'");
                    
                	while (true) {
                		System.out.println("ready to serve");
						String xml = read.nextLine();
                                                
                                                System.out.println(xml+"...hi4huial");
                                                
						XMLRequest request = new XMLRequest(xml);
                                                
                                                System.out.println(request.getRequestID()+"...hi4huial");
                                                
						if (request.getRequestID() == Constants.LOGIN_REQUEST_ID) {
							System.out.println("server sees login request!!!");
                                                    user_id = request.getUserID();
						}
						if (request.getActionID() == Constants.SELECT) {
							ReadServlet rServelet = new ReadServlet(request, this);
							Thread t = new Thread(rServelet);
							t.start();
							threadCount++;
						}else if (request.getActionID() == Constants.UPDATE) {
							UpdateServlet uServlet = new UpdateServlet(request, this);
							Thread t = new Thread(uServlet);
							t.start();
							threadCount++;
						}
						
					}
                /*
                    XML_parser_API login_regist_xml = new XML_parser_API(read.nextLine());
                    if (login_regist_xml.getRootTagName().equals(String.format("%d"
                            ,Constants.LOGIN_REQUEST_ID))){
                        if (!processLogin(read, write, login_regist_xml)){ //incorrect login for MAX_LOGIN_TRAIL trails
                            write.println("FORCE CLIENT SHUTDOWN");
                            incoming.close();
                        }
                    }
                    else if (login_regist_xml.getRootTagName().equals(String.format("%d"
                            ,Constants.REGIST_REQUEST_ID))){
                        //System.out.println("REGISTRATION REQ RECEIVED");
                        while(true){
                            if (login_regist_xml.getTagValue("ver_code").equals(VERIFICATION_CODE)){ //TODO: verification code is hardcoded now...modify this later (maybe phase 3?)
                                while(true){
                                    if (processRegist(read, write, login_regist_xml)){
                                       write.println("REGISTRATION_ACCEPTED"); 
                                       break;
                                    }
                                    else{
                                       write.println("username is already used. please use another username."); 
                                       XML_parser_API tmp_xml_parser = new XML_parser_API(read.nextLine());
                                       login_regist_xml = tmp_xml_parser;
                                    }
                                }
                                break;
                            }
                            else{
                                write.println("incorrect verification code. please register with a correct verification code.");
                                XML_parser_API tmp_xml_parser = new XML_parser_API(read.nextLine());
                                login_regist_xml = tmp_xml_parser;
                            }
                        }
                        
                    }
                    else{
                        System.out.println("ERROR: cannot pass the login/regist page...something went wrong");
                    }
                         
                        write.println("welcome, "+user_id+". please enter your message to be echoed. enter \"exit\" to exit.");
                        while(read.hasNextLine()){
                            String line = read.nextLine();
                            if (line.trim().toLowerCase().equals("exit")){
                                //done = true;
                                write.println("BYE"); //signal the client to exit
                                incoming.close();
                                break;
                            }
                            write.println("Echo: "+line);
                        }
*/
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
                    out.println("LOGIN_ACCEPTED");
                    //TODO: instead of sending client "LOGIN_ACCEPTED", send client the xml to display his homepage
                    
                    //String userHomeBoardXMLstring = getUserHomeBoardXMLstring();
                    
                    
                    
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