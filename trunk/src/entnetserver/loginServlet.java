package entnetserver;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import sun.nio.cs.ext.DBCS_IBM_EBCDIC_Decoder;
import Constants.Constants;
import JDBC.DataBase;
import Security.SharedKey;
import XML.XMLRequest;

public class loginServlet extends Servelet implements Runnable{

	public loginServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		SharedKey sk = SharedKey.getInstance();
		
		String loginUname = null;
		try {
			loginUname = new String(sk.sessionKeyDecrypt(handle.k_session, 
					xmlRequest.requestData.get("user_id")));
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidAlgorithmParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String loginPwd = null;
		try {
			loginPwd = new String(sk.sessionKeyDecrypt(handle.k_session, 
					xmlRequest.requestData.get("password")));
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidAlgorithmParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String loginQuery = "select aes_decrypt(user_id, '"+ThreadedHandler.db_pwd+"') as user_id,"
                        + "aes_decrypt(user_pwd,'"+ThreadedHandler.db_pwd+"') as user_pwd,"
                        + "aes_decrypt(contact_info,'"+ThreadedHandler.db_pwd+"') as contact_info,"
                        + "aes_decrypt(role_id,'"+ThreadedHandler.db_pwd+"') as role_id"
                        + " from user where aes_decrypt(user_id, '"+ThreadedHandler.db_pwd+"') = \"" + loginUname + 
				"\" and aes_decrypt(user_pwd,'"+ThreadedHandler.db_pwd+"') = \"" + loginPwd + "\";";
		//String k_db_str = this.handle.getKdb().skey.toString();
                
       //System.out.println("login query: "+loginQuery);
                
		DataBase dB =  handle.getSysDB();
		ResultSet rSet = dB.DoQuery(loginQuery);
		try {
			if (rSet.first()) {
				handle.roleID = rSet.getString("role_id");
				xmlRequest.setRequestDetail(Constants.TRUE);
				xmlRequest.setUserID(loginUname);
				xmlRequest.setSessionID(handle.roleID);
				handle.setUserID(loginUname);
			}else {
				xmlRequest.setRequestDetail(Constants.FALSE);
			}
			handle.callBackResult(xmlRequest);
		} catch (SQLException e) {
			System.out.println("SQLfault at login");
			xmlRequest.setRequestDetail(Constants.FALSE);
			handle.callBackResult(xmlRequest);
			e.printStackTrace();
		} 
	}

	
	
	
}
