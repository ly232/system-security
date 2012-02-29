/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import XML.XMLRequest;
import java.util.ArrayList;
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
    
    public ClientMain(){
        controller = EntNetClient.getInstance(this);
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
        
        public void LoginToHome(){
            lui.setVisible(false);
            uui = new UserHomeBoard(controller);
            uui.setVisible(true);
            //uui.populateInitScreen();
        }
        
        public void HomeToPerson(){
            uui.setVisible(false);
            pui = new PersonHomeBoard(controller);
            pui.setVisible(true);
            //
        }
        
        public void PersonToPerson(){
            
        }
        
        public void PersonToHome(){
            
        }
        
        
        
}
