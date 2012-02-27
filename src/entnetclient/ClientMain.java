/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetclient;

import view.*;

/**
 *
 * @author Lin
 */
public class ClientMain {
    static private EntNetClient controller;
    static public LoginUI lui;
    static public MainUI mui;
    
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
        
        public void killLoginUI(){
            lui.setVisible(false);
            mui = new MainUI(controller);
            mui.setVisible(true);
        }
        
}
