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
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

import Constants.Constants;

/**
 *
 * @author  Shuai
 */
public class PersonHomeBoard extends javax.swing.JFrame {
	DefaultListModel model = new DefaultListModel();
	public static EntNetClient controller;

	/** Creates new form UserHomeBoard */
	public PersonHomeBoard(EntNetClient ec) {
		initComponents();
		controller = ec;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
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
jPanel2 = new javax.swing.JPanel();
jLabel2 = new javax.swing.JLabel();
jLabelLocation = new javax.swing.JLabel();
jPanel3 = new javax.swing.JPanel();
jLabel3 = new javax.swing.JLabel();
jLabelProject = new javax.swing.JLabel();
jPanel4 = new javax.swing.JPanel();
jLabel4 = new javax.swing.JLabel();
jScrollPaneCA = new javax.swing.JScrollPane();
jTextAreaCA = new javax.swing.JTextArea();
jPanel5 = new javax.swing.JPanel();
jLabel5 = new javax.swing.JLabel();
jScrollPaneDA = new javax.swing.JScrollPane();
jTextAreaDA = new javax.swing.JTextArea();
jPanel6 = new javax.swing.JPanel();
jLabel6 = new javax.swing.JLabel();
jTextFieldMessage = new javax.swing.JTextField();
jButtonFM = new javax.swing.JButton();
jButtonLogout = new javax.swing.JButton();
jButtonReturn = new javax.swing.JButton();
jButtonDeleteFriend = new javax.swing.JButton();
jLabel9 = new javax.swing.JLabel();

setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
jPanel1.setLayout(jPanel1Layout);
jPanel1Layout.setHorizontalGroup(
jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel1Layout.createSequentialGroup()
.addGap(20, 20, 20)
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabelContactInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel8)
.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGroup(jPanel1Layout.createSequentialGroup()
.addComponent(jLabel7)
.addGap(18, 18, 18)
.addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
.addContainerGap(80, Short.MAX_VALUE))
);

jPanel2.setBackground(new java.awt.Color(255, 255, 204));

jLabel2.setText("Current Location");

javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
jPanel2.setLayout(jPanel2Layout);
jPanel2Layout.setHorizontalGroup(
jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabelLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(20, Short.MAX_VALUE))
);
jPanel2Layout.setVerticalGroup(
jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel2)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(jLabelLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
.addContainerGap(107, Short.MAX_VALUE))
);

jPanel3.setBackground(new java.awt.Color(255, 255, 204));

jLabel3.setText("Current Project");

javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
jPanel3.setLayout(jPanel3Layout);
jPanel3Layout.setHorizontalGroup(
jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel3Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabelProject, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(20, Short.MAX_VALUE))
);
jPanel3Layout.setVerticalGroup(
jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel3Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel3)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(jLabelProject, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
.addContainerGap(126, Short.MAX_VALUE))
);

jPanel4.setBackground(new java.awt.Color(255, 255, 204));

jLabel4.setText("Company Announcement");

jTextAreaCA.setColumns(20);
jTextAreaCA.setRows(5);
jScrollPaneCA.setViewportView(jTextAreaCA);

javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
jPanel4.setLayout(jPanel4Layout);
jPanel4Layout.setHorizontalGroup(
jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel4Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jScrollPaneCA, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(36, Short.MAX_VALUE))
);
jPanel4Layout.setVerticalGroup(
jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel4Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel4)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jScrollPaneCA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
.addContainerGap(48, Short.MAX_VALUE))
);

jPanel5.setBackground(new java.awt.Color(255, 255, 204));

jLabel5.setText("Department Announcement");

jTextAreaDA.setColumns(20);
jTextAreaDA.setRows(5);
jScrollPaneDA.setViewportView(jTextAreaDA);

javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
jPanel5.setLayout(jPanel5Layout);
jPanel5Layout.setHorizontalGroup(
jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel5Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabel5)
.addComponent(jScrollPaneDA, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(44, Short.MAX_VALUE))
);
jPanel5Layout.setVerticalGroup(
jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel5Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel5)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
.addComponent(jScrollPaneDA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
);

jPanel6.setBackground(new java.awt.Color(255, 255, 204));

jLabel6.setText("Friend Messages");

jButtonFM.setText("Post");
jButtonFM.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonFMActionPerformed(evt);
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

javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
jPanel6.setLayout(jPanel6Layout);
jPanel6Layout.setHorizontalGroup(
jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel6Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
.addContainerGap(59, Short.MAX_VALUE))
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
.addContainerGap(138, Short.MAX_VALUE)
.addComponent(jButtonFM)
.addContainerGap())
);
jPanel6Layout.setVerticalGroup(
jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel6Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel6)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(18, 18, 18)
.addComponent(jButtonFM)
.addContainerGap(89, Short.MAX_VALUE))
);

jButtonLogout.setText("Logout");
jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButtonLogoutActionPerformed(evt);
}
});

jButtonReturn.setText("Return to home");
jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jButtonReturnActionPerformed(evt);
}
});

jButtonDeleteFriend.setText("Delete Friend");
jButtonDeleteFriend.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
try {
	jButtonDeleteFriendActionPerformed(evt);
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

javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jButtonDeleteFriend)
.addGap(38, 38, 38))
.addGroup(layout.createSequentialGroup()
.addGap(35, 35, 35)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jButtonReturn))
.addGap(26, 26, 26)))
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
.addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jButtonLogout)
.addContainerGap())
);
layout.setVerticalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
.addContainerGap()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jButtonLogout)
.addGroup(layout.createSequentialGroup()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addGroup(layout.createSequentialGroup()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jPanel4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
.addGroup(layout.createSequentialGroup()
.addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addGroup(layout.createSequentialGroup()
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
.addGroup(layout.createSequentialGroup()
.addGap(24, 24, 24)
.addComponent(jButtonDeleteFriend)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
.addComponent(jButtonReturn)))))
.addGap(26, 26, 26))
);

pack();
}// </editor-fold>

	//GEN-END:initComponents
	private void jButtonDeleteFriendActionPerformed(
			java.awt.event.ActionEvent evt) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		controller.deleteFriend(jLabelID.getText());
	}

	private void jButtonReturnActionPerformed(java.awt.event.ActionEvent evt) {
		controller.returnUserHomePage();
	}

	private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {
		controller.quitClient();
	}

	private void jButtonFMActionPerformed(java.awt.event.ActionEvent evt) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		controller.postMessage(jLabelID.getText(), jTextFieldMessage.getText());
	}

	
	
	
	public void dataRefresh(ArrayList<?> a, String s) {
		if (s.equals(Constants.FRIENDLISTREGION)) {
			String[] x = (String[]) a.toArray(new String[a.size()]);
			for (int i = 0; i < a.size(); i++) {
				jList1.setListData(x);
			}

		}
		if (s.equals(Constants.REGION1)) {
			jLabelID.setText(a.get(0).toString());
			jLabelContactInfo.setText(a.get(1).toString());
			jPanel1.repaint();
		}
		if (s.equals(Constants.REGION2)) {
			jLabelLocation.setText(a.get(0).toString());
			jPanel2.repaint();
		}
		if (s.equals(Constants.REGION3)) {
			jLabelProject.setText(a.get(0).toString());
			jPanel3.repaint();
		}
		
		if (s.equals(Constants.REGION4)) {
			String[] x = (String[]) a.toArray(new String[a.size()]);
			String k = "";
			for (int i = 0; i < a.size(); i++) {
				k = k + "-" + x[i] + "\r\n";
			}
			jTextAreaCA.setText(k);
			jPanel4.repaint();
		}
		if (s.equals(Constants.REGION5)) {
			jTextAreaDA.setText("");
			jPanel5.repaint();
		}
		if (s.equals(Constants.REGION6)) {
			jPanel6.repaint();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PersonHomeBoard(controller).setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButtonDeleteFriend;
	private javax.swing.JButton jButtonFM;
	private javax.swing.JButton jButtonLogout;
	private javax.swing.JButton jButtonReturn;
	private javax.swing.JLabel jLabel1;
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
	private javax.swing.JLabel jLabelLocation;
	private javax.swing.JLabel jLabelProject;
	private javax.swing.JList jList1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPaneCA;
	private javax.swing.JScrollPane jScrollPaneDA;
	private javax.swing.JTextArea jTextAreaCA;
	private javax.swing.JTextArea jTextAreaDA;
	private javax.swing.JTextField jTextFieldMessage;

	// End of variables declaration//GEN-END:variables

	public void getArrayList(String[] strArr) {
		this.strArrFromClientLogic = strArr;
	}

	private String[] strArrFromClientLogic;

}