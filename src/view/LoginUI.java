/*
 * UI.java
 *
 * Created on __DATE__, __TIME__
 */

package view;

import java.io.IOException;
import java.io.PrintWriter;

import entnetclient.*;

/**
 *
 * @author  Shuai
 */
public class LoginUI extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static PrintWriter out;
        private EntNetClient controller; 
        private Thread clientMainThread;
	/** Creates new form UI */
	public LoginUI() {
		initComponents();
                controller = EntNetClient.getInstance();
                clientMainThread = controller.getClientMainThreadHandler();
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LoginUI().setVisible(true);
			}
		});
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jButtonLogin = new javax.swing.JButton();
		jButtonReg = new javax.swing.JButton();
		jLabelId = new javax.swing.JLabel();
		jLabelPwd = new javax.swing.JLabel();
		jLabelVcode = new javax.swing.JLabel();
		jLabelRegid = new javax.swing.JLabel();
		jLabelRegpwd = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabelRegcontact = new javax.swing.JLabel();
		jTextFieldId = new javax.swing.JTextField();
		jTextFieldPwd = new javax.swing.JTextField();
		jTextFieldVcode = new javax.swing.JTextField();
		jTextFieldRegid = new javax.swing.JTextField();
		jTextFieldRegpwd = new javax.swing.JTextField();
		jTextFieldRegname = new javax.swing.JTextField();
		jTextFieldRegcontact = new javax.swing.JTextField();
		jLabelRegrole = new javax.swing.JLabel();
		jTextFieldRegrole = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jButtonLogin.setText("Login");
		jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonLoginActionPerformed(evt);
			}
		});

		jButtonReg.setText("Regist");
		jButtonReg.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonRegActionPerformed(evt);
			}
		});

		jLabelId.setText("User Id");

		jLabelPwd.setText("Password");

		jLabelVcode.setText("Vcode");

		jLabelRegid.setText("RegID");

		jLabelRegpwd.setText("Regpassword");

		jLabel6.setText("Regname");

		jLabelRegcontact.setText("Regcontact");

		jTextFieldId.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFieldIdActionPerformed(evt);
			}
		});

		jTextFieldRegpwd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFieldRegpwdActionPerformed(evt);
			}
		});

		jLabelRegrole.setText("Regrole");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														javax.swing.GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup()
																.addGap(53, 53,
																		53)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jLabelId)
																				.addComponent(
																						jLabelPwd))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						jTextFieldPwd)
																				.addComponent(
																						jTextFieldId,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						86,
																						Short.MAX_VALUE))
																.addGap(90, 90,
																		90)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jLabelRegid)
																				.addComponent(
																						jLabelVcode)
																				.addComponent(
																						jLabelRegpwd)
																				.addComponent(
																						jLabel6)
																				.addComponent(
																						jLabelRegcontact)
																				.addComponent(
																						jLabelRegrole))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		45,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jTextFieldRegrole)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED))
																				.addComponent(
																						jTextFieldRegcontact)
																				.addComponent(
																						jTextFieldRegname)
																				.addComponent(
																						jTextFieldRegpwd)
																				.addComponent(
																						jTextFieldRegid,
																						javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(
																						jTextFieldVcode,
																						javax.swing.GroupLayout.Alignment.TRAILING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						104,
																						Short.MAX_VALUE)))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(97, 97,
																		97)
																.addComponent(
																		jButtonLogin)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		278,
																		Short.MAX_VALUE)
																.addComponent(
																		jButtonReg)))
								.addGap(87, 87, 87)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(57, 57, 57)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabelId)
												.addComponent(jLabelVcode)
												.addComponent(
														jTextFieldId,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jTextFieldVcode,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabelRegid)
												.addComponent(
														jTextFieldRegid,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabelPwd)
												.addComponent(jLabelRegpwd)
												.addComponent(
														jTextFieldPwd,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jTextFieldRegpwd,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel6)
												.addComponent(
														jTextFieldRegname,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabelRegcontact)
												.addComponent(
														jTextFieldRegcontact,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabelRegrole)
												.addComponent(
														jTextFieldRegrole,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										68, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jButtonLogin)
												.addComponent(jButtonReg))
								.addGap(55, 55, 55)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jTextFieldRegpwdActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButtonRegActionPerformed(java.awt.event.ActionEvent evt) {
		EntNetClient.clientRegist(
                        jTextFieldVcode.getText(), 
                        jTextFieldRegid.getText(), 
                        jTextFieldRegpwd.getText(), 
                        jTextFieldRegname.getText(), 
                        jTextFieldRegcontact.getText(), 
                        jTextFieldRegrole.getText()
                        );
	}

	private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {
            try{
                /*
                EntNetClient.clientLogin(
                        jTextFieldId.getText(),
                        jTextFieldPwd.getText()
                        );*/
            }catch(Exception e){};
	}

	private void jTextFieldIdActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	/**
	 * @param args the command line arguments
	 */

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButtonLogin;
	private javax.swing.JButton jButtonReg;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabelId;
	private javax.swing.JLabel jLabelPwd;
	private javax.swing.JLabel jLabelRegcontact;
	private javax.swing.JLabel jLabelRegid;
	private javax.swing.JLabel jLabelRegpwd;
	private javax.swing.JLabel jLabelRegrole;
	private javax.swing.JLabel jLabelVcode;
	private javax.swing.JTextField jTextFieldId;
	private javax.swing.JTextField jTextFieldPwd;
	private javax.swing.JTextField jTextFieldRegcontact;
	private javax.swing.JTextField jTextFieldRegid;
	private javax.swing.JTextField jTextFieldRegname;
	private javax.swing.JTextField jTextFieldRegpwd;
	private javax.swing.JTextField jTextFieldRegrole;
	private javax.swing.JTextField jTextFieldVcode;
	// End of variables declaration//GEN-END:variables

}