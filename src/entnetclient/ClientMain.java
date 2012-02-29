/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import XML.XMLRequest;
import java.util.ArrayList;
import java.util.HashMap;
import view.*;
import Constants.*;

/**
 *
 * @author Lin
 */
public class ClientMain {
    static private EntNetClient controller;
    static public LoginUI lui;
    static public UserHomeBoard uui;
    static public PersonHomeBoard pui;
    
    static private String currUI; //currently displaying UI
    
    public ClientMain(){
        controller = EntNetClient.getInstance(this);
        currUI = "LoginUI";
    }
    
    public void quit(){
        System.exit(0);
    }
    
        public static void main(String[] args) {
            ClientMain cm = new ClientMain();
            java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				lui = new LoginUI(controller);
                                lui.setVisible(true);
			}
		});
	}
        
        public void giveArrayListToUI(ArrayList<String> al, String RegionID){
            if (currUI.equals("UserHomeBoard")){
                //uui.dataRefresh(al, RegionID);
            }
            else if (currUI.equals("PersonHomeBoard")){
                pui.dataRefresh(al, RegionID);
            }
            else{
                System.err.println("ERROR: encountered invalid board id in ClientMain::giveHashMapToUI");
                return;
            }
        }
        
        
        /*
        public void changeCurrUIid(String UIid){
            currUI = UIid;
        }*/
        
        
        public void LoginToHome(){
            lui.setVisible(false);
            uui = new UserHomeBoard(controller);
            uui.setVisible(true);
            //uui.populateInitScreen();
            currUI = Constants.UserHomeBoard;
        }
        
        public void HomeToPerson(){
            uui.setVisible(false);
            pui = new PersonHomeBoard(controller);
            pui.setVisible(true);
            currUI = Constants.PersonHomeBoard;
        }
        
        public void PersonToPerson(){
            //dont need to do anything here...just stay in the same gui frame. data content will be refreshed by another method
        }
        
        public void PersonToHome(){
            pui.setVisible(false);
            uui = new UserHomeBoard(controller);
            uui.setVisible(true);
            currUI = Constants.UserHomeBoard;
        }
        
        
        
}
