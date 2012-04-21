/*
 * UserHomeBoard.java
 *
 * Created on __DATE__, __TIME__
 */

package view;

import entnetclient.EntNetClient;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;

import Constants.Constants;

/**
 *
 * @author  Shuai
 */
public class UserHomeBoard extends javax.swing.JFrame {

	public Boolean showUpdateCompanyBoardButton = false;
	public Boolean showUpdateDeptBoardButton = false;

	DefaultListModel model = new DefaultListModel();
	public static EntNetClient controller;

	/** Creates new form UserHomeBoard */
	public UserHomeBoard(EntNetClient ec) {

		controller = ec;
		String roleID = controller.roleID;
		System.out.println("roleID from ui: " + roleID);
		if (roleID.equals(Constants.BOSS_ROLE_ID))
			this.showUpdateCompanyBoardButton = true;
		if (roleID.equals(Constants.DEPTHEAD_ROLE_ID))
			this.showUpdateDeptBoardButton = true;

		initComponents();
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

jScrollPane1 = new javax.swing.JScrollPane();
jList1 = new javax.swing.JList();
jPanel1 = new javax.swing.JPanel();
jLabel1 = new javax.swing.JLabel();
jLabel7 = new javax.swing.JLabel();
jLabelID = new javax.swing.JLabel();
jLabel8 = new javax.swing.JLabel();
jLabelContactInfo = new javax.swing.JLabel();
jTextField1 = new javax.swing.JTextField();
jButtonCon = new javax.swing.JButton();
jPanel2 = new javax.swing.JPanel();
jLabel2 = new javax.swing.JLabel();
jButtonLocation = new javax.swing.JButton();
jComboBoxLocation = new javax.swing.JComboBox();
jLabellocation = new javax.swing.JLabel();
jPanel3 = new javax.swing.JPanel();
jLabel3 = new javax.swing.JLabel();
jButtonProject = new javax.swing.JButton();
jComboBoxProject = new javax.swing.JComboBox();
jLabelproject = new javax.swing.JLabel();
jPanel4 = new javax.swing.JPanel();
jLabel4 = new javax.swing.JLabel();
jScrollPaneCA = new javax.swing.JScrollPane();
jTextAreaCA = new javax.swing.JTextArea();
jButtonCom = new javax.swing.JButton();
jTextFieldCom = new javax.swing.JTextField();
jPanel5 = new javax.swing.JPanel();
jLabel5 = new javax.swing.JLabel();
jScrollPaneDA = new javax.swing.JScrollPane();
jTextAreaDA = new javax.swing.JTextArea();
jButtonDep = new javax.swing.JButton();
jComboBoxDep = new javax.swing.JComboBox();
jTextFieldDep = new javax.swing.JTextField();
jButtonSwitch = new javax.swing.JButton();
jPanel6 = new javax.swing.JPanel();
jLabel6 = new javax.swing.JLabel();
jScrollPane3 = new javax.swing.JScrollPane();
jTextAreaFM = new javax.swing.JTextArea();
jButtonLogout = new javax.swing.JButton();
jButtonAdd = new javax.swing.JButton();
jLabel9 = new javax.swing.JLabel();
jTextFieldAF = new javax.swing.JTextField();
jPanelFR = new javax.swing.JPanel();
jLabel10 = new javax.swing.JLabel();
jScrollPane2 = new javax.swing.JScrollPane();
jTextAreaFR = new javax.swing.JTextArea();
jButtonRefresh = new javax.swing.JButton();

setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

jButtonCom.setVisible(false);
jTextFieldCom.setVisible(false);
jButtonDep.setVisible(false);
jTextFieldDep.setVisible(false);
jComboBoxDep.setVisible(false);
jButtonSwitch.setVisible(false);

if (this.showUpdateCompanyBoardButton){
	jButtonCom.setVisible(true);
	jTextFieldCom.setVisible(true);
	jComboBoxDep.setVisible(true);
	jButtonSwitch.setVisible(true);
}
if (this.showUpdateDeptBoardButton){
	jButtonDep.setVisible(true);
	jTextFieldDep.setVisible(true);
}

jList1.setModel(new javax.swing.AbstractListModel() {
	String[] strings = { };

	public int getSize() {
		return strings.length;
	}

	public Object getElementAt(int i) {
		return strings[i];
	}
});
jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 MouseListener   mouseListener   =   new   MouseAdapter()   { 
       public   void   mouseClicked(MouseEvent   e)   { 
                       int   index   =   jList1.locationToIndex(e.getPoint()); 
                       //System.out.println( "Double   clicked   on   Item   "   +   index); 
                       try{
						   controller.clientViewOtherPersonBoard((String)jList1.getSelectedValue(), Constants.OTHER_TO_OTHER_VIEW);
						   }catch(Exception execp){};

                 
       } 
}; 
jList1.addMouseListener(mouseListener);
jScrollPane1.setViewportView(jList1);

jPanel1.setBackground(new java.awt.Color(255, 255, 204));

jLabel1.setText("Personal info");

jLabel7.setText("User ID:");

jLabel8.setText("Contact Info:");

jTextField1.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jTextField1ActionPerformed(evt);
}
});

jButtonCon.setText("Update");
jButtonCon.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonConActionPerformed(evt);
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

javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
jPanel1.setLayout(jPanel1Layout);
jPanel1Layout.setHorizontalGroup(
jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel1Layout.createSequentialGroup()
.addGap(20, 20, 20)
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabel8)
.addComponent(jLabel1)
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
.addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabelContactInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jButtonCon)
.addGroup(jPanel1Layout.createSequentialGroup()
.addComponent(jLabel7)
.addGap(18, 18, 18)
.addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
.addContainerGap(57, Short.MAX_VALUE))
);
jPanel1Layout.setVerticalGroup(
jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel1Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel1)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jLabel7)
.addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jLabel8)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jLabelContactInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(jButtonCon)
.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
);

jPanel2.setBackground(new java.awt.Color(255, 255, 204));

jLabel2.setText("Current Location");

jButtonLocation.setText("Update");
jButtonLocation.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonLocationActionPerformed(evt);
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



jLabellocation.setText("jLabel11");

javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
jPanel2.setLayout(jPanel2Layout);
jPanel2Layout.setHorizontalGroup(
jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jButtonLocation)
.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(55, Short.MAX_VALUE))
.addGroup(jPanel2Layout.createSequentialGroup()
.addComponent(jLabellocation)
.addContainerGap(146, Short.MAX_VALUE))
.addGroup(jPanel2Layout.createSequentialGroup()
.addComponent(jComboBoxLocation, 0, 141, Short.MAX_VALUE)
.addGap(48, 48, 48))))
);
jPanel2Layout.setVerticalGroup(
jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel2)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jLabellocation)
.addGap(18, 18, 18)
.addComponent(jComboBoxLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
.addComponent(jButtonLocation)
.addGap(32, 32, 32))
);

jPanel3.setBackground(new java.awt.Color(255, 255, 204));

jLabel3.setText("Current Project");

jButtonProject.setText("Update");
jButtonProject.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonProjectActionPerformed(evt);
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



jLabelproject.setText("jLabel11");

javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
jPanel3.setLayout(jPanel3Layout);
jPanel3Layout.setHorizontalGroup(
jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel3Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jButtonProject)
.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
.addComponent(jLabelproject)
.addComponent(jComboBoxProject, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(55, Short.MAX_VALUE))
);
jPanel3Layout.setVerticalGroup(
jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel3Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel3)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jLabelproject)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jComboBoxProject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
.addComponent(jButtonProject)
.addContainerGap())
);

jPanel4.setBackground(new java.awt.Color(255, 255, 204));

jLabel4.setText("Company Announcement");

jTextAreaCA.setColumns(20);
jTextAreaCA.setRows(5);
jScrollPaneCA.setViewportView(jTextAreaCA);

jButtonCom.setText("Update");
jButtonCom.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonComActionPerformed(evt);
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

javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
jPanel4.setLayout(jPanel4Layout);
jPanel4Layout.setHorizontalGroup(
jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel4Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jScrollPaneCA, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
.addComponent(jTextFieldCom, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
.addComponent(jButtonCom)))
.addContainerGap())
);
jPanel4Layout.setVerticalGroup(
jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel4Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel4)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jScrollPaneCA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(18, 18, 18)
.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jButtonCom)
.addComponent(jTextFieldCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
);

jPanel5.setBackground(new java.awt.Color(255, 255, 204));

jLabel5.setText("Department Announcement");

jTextAreaDA.setColumns(20);
jTextAreaDA.setRows(5);
jScrollPaneDA.setViewportView(jTextAreaDA);

jButtonDep.setText("Update");
jButtonDep.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonDepActionPerformed(evt);
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



jButtonSwitch.setText("Switch");

javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
jPanel5.setLayout(jPanel5Layout);
jPanel5Layout.setHorizontalGroup(
jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel5Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel5Layout.createSequentialGroup()
.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabel5)
.addComponent(jScrollPaneDA, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jButtonSwitch)
.addComponent(jComboBoxDep, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap())
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
.addComponent(jTextFieldDep, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
.addGap(14, 14, 14)
.addComponent(jButtonDep)
.addContainerGap())))
);
jPanel5Layout.setVerticalGroup(
jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel5Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel5)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel5Layout.createSequentialGroup()
.addComponent(jComboBoxDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(33, 33, 33)
.addComponent(jButtonSwitch))
.addComponent(jScrollPaneDA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jButtonDep, javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jTextFieldDep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
);

jPanel6.setBackground(new java.awt.Color(255, 255, 204));

jLabel6.setText("Friend Messages");

jTextAreaFM.setColumns(20);
jTextAreaFM.setRows(5);
jScrollPane3.setViewportView(jTextAreaFM);

javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
jPanel6.setLayout(jPanel6Layout);
jPanel6Layout.setHorizontalGroup(
jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel6Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
.addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
.addContainerGap(110, Short.MAX_VALUE))
);
jPanel6Layout.setVerticalGroup(
jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel6Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel6)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
.addContainerGap(20, Short.MAX_VALUE))
);

jButtonLogout.setText("Logout");
jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButtonLogoutActionPerformed(evt);
}
});

jButtonAdd.setText("Add Friend");
jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonAddActionPerformed(evt);
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

jLabel9.setText("Friend List");

jTextFieldAF.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jTextFieldAFActionPerformed(evt);
}
});

jLabel10.setText("Friends Request");

jTextAreaFR.setColumns(20);
jTextAreaFR.setRows(5);
jScrollPane2.setViewportView(jTextAreaFR);

javax.swing.GroupLayout jPanelFRLayout = new javax.swing.GroupLayout(jPanelFR);
jPanelFR.setLayout(jPanelFRLayout);
jPanelFRLayout.setHorizontalGroup(
jPanelFRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanelFRLayout.createSequentialGroup()
.addComponent(jLabel10)
.addContainerGap(32, Short.MAX_VALUE))
.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
);
jPanelFRLayout.setVerticalGroup(
jPanelFRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanelFRLayout.createSequentialGroup()
.addComponent(jLabel10)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
);

jButtonRefresh.setText("Refresh");

javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup()
.addContainerGap()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
.addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jButtonAdd)
.addComponent(jTextFieldAF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
.addComponent(jPanelFR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
.addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
.addComponent(jButtonRefresh, 0, 0, Short.MAX_VALUE)
.addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addGap(18, 18, 18))
);
layout.setVerticalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup()
.addContainerGap()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addGroup(layout.createSequentialGroup()
.addComponent(jButtonRefresh)
.addGap(18, 18, 18)
.addComponent(jButtonLogout))
.addGroup(layout.createSequentialGroup()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addGroup(layout.createSequentialGroup()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jPanel4, 0, 184, Short.MAX_VALUE)
.addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
.addGroup(layout.createSequentialGroup()
.addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(77, 77, 77)))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addGroup(layout.createSequentialGroup()
.addGap(97, 97, 97)
.addComponent(jTextFieldAF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
.addComponent(jButtonAdd))
.addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
.addGap(27, 27, 27))
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
.addComponent(jPanelFR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(104, 104, 104))))
);

pack();
}// </editor-fold>

	//GEN-END:initComponents
	private void jButtonDepActionPerformed(java.awt.event.ActionEvent evt)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		String new_dept_msg = this.jTextFieldDep.getText();
		if (new_dept_msg.length() == 0) {
			System.out.println("cannot update new contact info to null");
		} else {
			controller.postDeptMessage(new_dept_msg);
		}
	}

	void jButtonComActionPerformed(java.awt.event.ActionEvent evt)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		String new_company_msg = this.jTextFieldCom.getText();
		if (new_company_msg.length() == 0) {
			System.out.println("cannot update new contact info to null");
		} else {
			controller.postCompanyMessage(new_company_msg);
		}
	}

	private void jButtonProjectActionPerformed(java.awt.event.ActionEvent evt)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		String temp = (String) this.jComboBoxProject.getSelectedItem();
		this.jLabelproject.setText(temp);
		this.controller.clientUpdateRegion(Constants.REGION3, temp);
	}

	private void jButtonLocationActionPerformed(java.awt.event.ActionEvent evt)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		String temp = (String) this.jComboBoxLocation.getSelectedItem();
		this.jLabellocation.setText(temp);
		this.controller.clientUpdateRegion(Constants.REGION2, temp);

	}

	private void jButtonConActionPerformed(java.awt.event.ActionEvent evt)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		// TODO add your handling code here:
		String new_contact_info = jTextField1.getText();
		if (new_contact_info.length() == 0) {
			System.out.println("cannot update new contact info to null");
		} else {
			controller.clientUpdateRegion(Constants.REGION1, new_contact_info);
		}
		//System.out.println("button clicked!!!");
	}

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jTextFieldAFActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		controller.friendRequest(jTextFieldAF.getText());
	}

	private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {
		controller.quitClient();
	}

	public void dataRefresh(ArrayList<?> a, String s) {
		if (s.equals(Constants.FRIENDLISTREGION)) {
			String[] x = (String[]) a.toArray(new String[a.size()]);

			for (int i = 0; i < a.size(); i++) {
				//model.add(i, a.get(i));
				//System.out.println("friend: "+a.get(i));
				//jScrollPane1.repaint();
				jList1.setListData(x);
			}

		}

		if (s.equals(Constants.NOTIFYREGION)) {
			String[] x = (String[]) a.toArray(new String[a.size()]);
			String k = "";
			for (int i = 0; i < a.size(); i++) {
				System.out
						.println("================testing====================");
				System.out.println(x[i]);
				k += "-" + x[i] + "\r\n";
			}

			jTextAreaFR.setText(k);

			//jTextAreaCA.setText("");
			jPanelFR.repaint();
		}

		if (s.equals(Constants.REGION1)) {
			jLabelID.setText(a.get(0).toString());
			jLabelContactInfo.setText(a.get(1).toString());
			jPanel1.repaint();

			//System.out.println(a);

		}
		if (s.equals(Constants.REGION2)) {

			//jComboBoxLocation.addItem(a);
			this.jLabellocation.setText(a.get(0).toString());
			jPanel2.repaint();

		}
		if (s.equals(Constants.VALID_LOCATION)) {
			for (int i = 0; i < a.size(); i++) {
				jComboBoxLocation.addItem(a.get(i).toString());
			}
			jComboBoxLocation.repaint();
		}
		if (s.equals(Constants.VALID_DEPT)) {
			for (int i = 0; i < a.size(); i++) {
				this.jComboBoxDep.addItem(a.get(i).toString());
			}
			jComboBoxLocation.repaint();
		}
		if (s.equals(Constants.REGION3)) {
			//jComboBoxProject.addItem(a);
			this.jLabelproject.setText(a.get(0).toString());
			jPanel3.repaint();
		}
		if (s.equals(Constants.VALID_PROJECT)) {
			for (int i = 0; i < a.size(); i++) {
				jComboBoxProject.addItem(a.get(i).toString());
			}
			jComboBoxProject.repaint();
		}
		if (s.equals(Constants.REGION4)) {

			//System.out.println("company msg="+a.get(0));

			String[] x = (String[]) a.toArray(new String[a.size()]);
			String k = "";
			for (int i = 0; i < a.size(); i++) {
				k += "-" + x[i] + "\r\n";
			}

			jTextAreaCA.setText(k);

			//jTextAreaCA.setText("");
			jPanel4.repaint();
		}
		if (s.equals(Constants.REGION5)) {
			//jTextAreaDA.setText("");

			String[] x = (String[]) a.toArray(new String[a.size()]);
			String k = "";
			for (int i = 0; i < a.size(); i++) {
				k += "-" + x[i] + "\r\n";
			}

			jTextAreaDA.setText(k);

			jPanel5.repaint();
		}
		if (s.equals(Constants.REGION6)) {
			String[] x = (String[]) a.toArray(new String[a.size()]);
			String k = "";
			for (int i = 0; i < a.size(); i++) {
				k += "-" + x[i] + "\r\n";
			}

			jTextAreaFM.setText(k);
			jPanel6.repaint();
		}
	}

	/**
	 * @param args the command line arguments
	 */

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButtonAdd;
	private javax.swing.JButton jButtonCom;
	private javax.swing.JButton jButtonCon;
	private javax.swing.JButton jButtonDep;
	private javax.swing.JButton jButtonLocation;
	private javax.swing.JButton jButtonLogout;
	private javax.swing.JButton jButtonProject;
	private javax.swing.JButton jButtonRefresh;
	private javax.swing.JButton jButtonSwitch;
	private javax.swing.JComboBox jComboBoxDep;
	private javax.swing.JComboBox jComboBoxLocation;
	private javax.swing.JComboBox jComboBoxProject;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JLabel jLabelContactInfo;
	private javax.swing.JLabel jLabelID;
	private javax.swing.JLabel jLabellocation;
	private javax.swing.JLabel jLabelproject;
	private javax.swing.JList jList1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanelFR;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPaneCA;
	private javax.swing.JScrollPane jScrollPaneDA;
	private javax.swing.JTextArea jTextAreaCA;
	private javax.swing.JTextArea jTextAreaDA;
	private javax.swing.JTextArea jTextAreaFM;
	private javax.swing.JTextArea jTextAreaFR;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextFieldAF;
	private javax.swing.JTextField jTextFieldCom;
	private javax.swing.JTextField jTextFieldDep;

	// End of variables declaration//GEN-END:variables

	public void getArrayList(String[] strArr) {
		this.strArrFromClientLogic = strArr;
	}

	public String getTextField1() {
		return this.jTextField1.getText();

	}

	private String[] strArrFromClientLogic;

}