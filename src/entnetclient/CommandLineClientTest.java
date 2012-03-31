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
		}else if (request.equals("Show My Region")) {

			controller.returnUserHomePage();

		}else if (request.equals("Post Messege")) {
			Put("input the friend ID");
			String friend_id = Gets();
			Put("input the the messege content");
			String messageString = Gets();
			controller.postMessage(friend_id, messageString);
		}else if (request.equals("Switch to")) {
			Put("input the friend ID");
			String friend_id = Gets();
			try {
				Put(friend_id);
				controller.clientViewOtherPersonBoard(friend_id, null);
			} catch (IOException e) {
				System.out.println("Show all Region Failed");
				e.printStackTrace();
			}
		}else {
			Put("invalide command");
			Put("please input again");
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
			Put("invalid username or password");
			login();
		}
	}
	
	public void personPanelCallback(String region, ArrayList<String> str){
			try {
				   Put(region);
					//Put(str.toString());
					Object[] strObjects = str.toArray();
					for (int i = 0; i < strObjects.length; i++) {
						Put(strObjects[i].toString());
					}
					Put("");
			} catch (Exception e) {
				
			}
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
