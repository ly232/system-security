package entnetclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;

import sun.security.action.PutAllAction;

import Constants.Constants;

import XML.XMLRequest;

import com.sun.java_cup.internal.runtime.Scanner;


public class CommandLineClientTest {
	
	
	  public EntNetClient controller;
	  private BufferedReader bReader;
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
		}
	      
	}
	
	
	public void LoginCallBack(XMLRequest rq) {
		if( rq.getRequestDetail().equals(Constants.TRUE)){
			PersonalPanel();
			Put("Login Success");
		}else {
			login();
		}
	}
	
	public void personPanelCallback(String region, HashMap<String,String> result){
			//Put(region);
			Put(result.toString());
	}
	
	
	public void Put(String str){
		System.out.println( str);
	}
	
	public void login(){
		    try {
		    	Put("input the user name");
				//String user_id = bReader.readLine();
				Put("input the password");
				//String friend_id = bReader.readLine();
				//controller.clientLogin(user_id, friend_id);
				controller.clientLogin("tao", "123");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void PersonalPanel(){
			System.out.println("");
			
	}
	
}
