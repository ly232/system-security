/*
 * UI.java
 *
 * Created on __DATE__, __TIME__
 */

package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

import entnetclient.*;

/**
 *
 * @author  Shuai
 */
public class LoginUI extends javax.swing.JFrame {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	//public static PrintWriter out;
	public static EntNetClient controller;

	/** Creates new form UI */
	public LoginUI(EntNetClient ec) {
		initComponents();

		controller = ec;

		//controller = EntNetClient.getInstance();
		//clientMainThread = controller.getClientMainThreadHandler();
		//controller.startClientMainThread();

	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jButtonLogin = new javax.swing.JButton();
		jButtonReg = new javax.swing.JButton();
		jLabelId = new javax.swing.JLabel();
		jLabelPwd = new javax.swing.JLabel();
		jLabelRegid = new javax.swing.JLabel();
		jLabelRegpwd = new javax.swing.JLabel();
		jLabelRegcontact = new javax.swing.JLabel();
		jTextFieldId = new javax.swing.JTextField();
		jTextFieldRegid = new javax.swing.JTextField();
		jTextFieldRegcontact = new javax.swing.JTextField();
		jLabelRegrole = new javax.swing.JLabel();
		jTextFieldRegrole = new javax.swing.JTextField();
		jLabelVcode = new javax.swing.JLabel();
		jTextFieldDeptName = new javax.swing.JTextField();
		jLabelDeptName = new javax.swing.JLabel();
		jLabelRegPwd = new javax.swing.JPasswordField();
		jLabelLoginPwd = new javax.swing.JPasswordField();
		jLabelLocation = new javax.swing.JLabel();
		jLabelProject = new javax.swing.JLabel();
		jTextFieldLocation = new javax.swing.JTextField();
		jTextFieldProject = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jPasswordFieldVCode = new javax.swing.JPasswordField();

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
				try {
					jButtonRegActionPerformed(evt);
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidAlgorithmParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		jLabelId.setText("User Id");

		jLabelPwd.setText("Password");

		jLabelRegid.setText("User ID");

		jLabelRegpwd.setText("Password");

		jLabelRegcontact.setText("Contact Information");

		jTextFieldId.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFieldIdActionPerformed(evt);
			}
		});

		jLabelRegrole.setText("Role ID");

		jLabelVcode.setText("Verification Code");

		jLabelDeptName.setText("Department ID");

		jLabelLocation.setText("Location ID");

		jLabelProject.setText("Project ID");

		jLabel1.setText("REGISTRATION");

		jLabel2.setText("LOGIN");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(53, 53,
																		53)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabelId)
																								.addGap(19,
																										19,
																										19))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabelPwd)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						jLabelLoginPwd,
																						0,
																						0,
																						Short.MAX_VALUE)
																				.addComponent(
																						jTextFieldId,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						86,
																						Short.MAX_VALUE)
																				.addComponent(
																						jLabel2))
																.addGap(90, 90,
																		90)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						jLabelRegid)
																				.addComponent(
																						jLabelRegpwd)
																				.addComponent(
																						jLabelRegcontact,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						jLabelRegrole)
																				.addComponent(
																						jLabelVcode)
																				.addComponent(
																						jLabelProject)
																				.addComponent(
																						jLabelLocation)
																				.addComponent(
																						jLabelDeptName)))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(97, 97,
																		97)
																.addComponent(
																		jButtonLogin)))
								.addGap(57, 57, 57)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jPasswordFieldVCode,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE)
												.addComponent(
														jTextFieldProject,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE)
												.addComponent(
														jTextFieldLocation,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE)
												.addComponent(
														jTextFieldDeptName,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE)
												.addComponent(
														jButtonReg,
														javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														jTextFieldRegrole,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE)
												.addComponent(
														jTextFieldRegcontact,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE)
												.addComponent(
														jTextFieldRegid,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE)
												.addComponent(
														jLabelRegPwd,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														94, Short.MAX_VALUE))
								.addGap(93, 93, 93))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(370, 370, 370)
								.addComponent(jLabel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										120, Short.MAX_VALUE)
								.addGap(148, 148, 148)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(30, 30, 30)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														jLabel1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														27,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel2))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabelId)
																				.addComponent(
																						jTextFieldId,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabelPwd)
																				.addComponent(
																						jLabelLoginPwd,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabelRegid)
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
																				.addComponent(
																						jLabelRegpwd)
																				.addComponent(
																						jLabelRegPwd,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jTextFieldRegcontact,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabelRegcontact))))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jTextFieldRegrole,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabelDeptName)
																				.addComponent(
																						jTextFieldDeptName,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabelLocation)
																				.addComponent(
																						jTextFieldLocation,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabelProject)
																				.addComponent(
																						jTextFieldProject,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabelVcode)
																				.addComponent(
																						jPasswordFieldVCode,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		22,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jButtonLogin)
																				.addComponent(
																						jButtonReg))
																.addGap(55, 55,
																		55))
												.addComponent(jLabelRegrole))));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jButtonRegActionPerformed(java.awt.event.ActionEvent evt)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		if (jTextFieldRegid.getText().equals("")
				|| jTextFieldRegrole.getText().equals("")
				|| jLabelRegPwd.getPassword().equals(null)
				|| jTextFieldRegcontact.getText().equals("")
				|| this.jTextFieldLocation.getText().equals("")
				|| this.jTextFieldDeptName.getText().equals("")
				|| this.jTextFieldProject.getText().equals("")
				|| this.jPasswordFieldVCode.getPassword().equals(null))
			JOptionPane.showMessageDialog(null,
					"Please fill in all registration fields!");
		else {
			controller
					.clientRegist(jTextFieldRegid.getText(), new String(
							jLabelRegPwd.getPassword()), jTextFieldRegcontact
							.getText(), jTextFieldRegrole.getText(),
							this.jTextFieldDeptName.getText(),
							this.jTextFieldLocation.getText(),
							this.jTextFieldProject.getText(),
							new String(this.jPasswordFieldVCode.getPassword()));
		}

	}

	private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {

		try {
			controller.clientLogin(jTextFieldId.getText(), new String(
					jLabelLoginPwd.getPassword()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		;

	}

	private void jTextFieldIdActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	public static void checkRegist(Boolean b, String errMsg) {
		if (b == false) {
			JOptionPane.showMessageDialog(null, "Registration failed! REASON: "
					+ errMsg);
		} else
			JOptionPane.showMessageDialog(null,
					"Registration succeed, please login!");
	}

	public static void loginPopUp() {
		JOptionPane.showMessageDialog(null, "Login failed!");
	}

	/**
	 * @param args the command line arguments
	 */

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButtonLogin;
	private javax.swing.JButton jButtonReg;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabelDeptName;
	private javax.swing.JLabel jLabelId;
	private javax.swing.JLabel jLabelLocation;
	private javax.swing.JPasswordField jLabelLoginPwd;
	private javax.swing.JLabel jLabelProject;
	private javax.swing.JLabel jLabelPwd;
	private javax.swing.JPasswordField jLabelRegPwd;
	private javax.swing.JLabel jLabelRegcontact;
	private javax.swing.JLabel jLabelRegid;
	private javax.swing.JLabel jLabelRegpwd;
	private javax.swing.JLabel jLabelRegrole;
	private javax.swing.JLabel jLabelVcode;
	private javax.swing.JPasswordField jPasswordFieldVCode;
	private javax.swing.JTextField jTextFieldDeptName;
	private javax.swing.JTextField jTextFieldId;
	private javax.swing.JTextField jTextFieldLocation;
	private javax.swing.JTextField jTextFieldProject;
	private javax.swing.JTextField jTextFieldRegcontact;
	private javax.swing.JTextField jTextFieldRegid;
	private javax.swing.JTextField jTextFieldRegrole;
	// End of variables declaration//GEN-END:variables

}