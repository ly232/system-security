/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import XML.XMLRequest;
import java.util.ArrayList;
import java.util.HashMap;
import view.*;

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
        public static void main(String[] args) {
            ClientMain cm = new ClientMain();
            java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				lui = new LoginUI(controller);
                                lui.setVisible(true);
			}
		});
	}
        
        public void giveArrayListToUI(ArrayList<String> al){
            if (currUI.equals("UserHomeBoard")){
                String[] x = al.toArray(new String[al.size()]);
                
                uui.getArrayList(x);
            }
            else if (currUI.equals("PersonHomeBoard")){
                pui.getArrayList(al.toArray(new String[al.size()]));
            }
            else{
                System.out.println("ERROR: encountered invalid board id in ClientMain::giveHashMapToUI");
                return;
            }
        }
        
        public void changeCurrUIid(String UIid){
            currUI = UIid;
        }
        
        public void LoginToHome(){
            lui.setVisible(false);
            uui = new UserHomeBoard(controller);
            uui.setVisible(true);
            //uui.populateInitScreen();
            currUI = "UserHomeBoard";
        }
        
        public void HomeToPerson(){
            uui.setVisible(false);
            pui = new PersonHomeBoard(controller);
            pui.setVisible(true);
            currUI = "PersonHomeBoard";
        }
        
        public void PersonToPerson(){
            
        }
        
        public void PersonToHome(){
            currUI = "UserHomeBoard";
        }
        
        
        
}
