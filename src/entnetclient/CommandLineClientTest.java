package entnetclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import Constants.Constants;

import XML.XMLRequest;


public class CommandLineClientTest {
	  public EntNetClient controller;
	  private BufferedReader bReader;
	  private String userid;
	public static void main(String[] args){
		 CommandLineClientTest mainTest = new CommandLineClientTest();
		 mainTest.start();
	}
	
	boolean login = false;
	
	CommandLineClientTest(){
	     controller = new EntNetClient(this);
		  InputStreamReader stdin = new  InputStreamReader(System.in);
		   bReader = new BufferedReader(stdin);
	}
	
	public void start(){
	      Put("=== Client ===");
	    	  login();
	         // bReader.readLine();
		  while (true) {
			  Put("===============");
			  Put("input the commond");
			  Put("================");
			  try {
				String commandString = bReader.readLine();
				if (parseRequest(commandString)) {
					return;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	      
	}
	
	
	private String Gets(){
		try {
			String friend_id = bReader.readLine();
			return friend_id;
		} catch (IOException e) {
			Put("get string error");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean parseRequest(String request){
		if (request.equals("Add Friend")) {
			Put("Input the friend ID");
			String friend_id = Gets();
			controller.friendRequest(friend_id);
			return false;
		} else if (request.equals("Quit")) {
			Put("Quit the client");
			controller.quitClient();
			return true;
		}else if (request.equals("Delete Friend")) {
			Put("Input the friend ID");
			String friend_id = Gets();
			controller.deleteFriend(friend_id);
		}
		return false;
	}
	
	
	public void LoginCallBack(XMLRequest rq) {
		if( rq.getRequestDetail().equals(Constants.TRUE)){
			PersonalPanel();
			Put("Login Success");
			Put("=========");
			Put("");
		}else {
			login();
		}
	}
	
	public void personPanelCallback(String region, ArrayList<String> str){
			Put(region);
			Put(str.toString());
	}
	
	
	public void Put(String str){
		System.out.println( str);
	}
	
	public void login(){
		    try {
		    	Put("input the user name");
				String user_id = bReader.readLine();
				Put("input the password");
				String friend_id = bReader.readLine();
				controller.clientLogin(user_id, friend_id);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void PersonalPanel(){
			System.out.println("");
			
	}
	
}
