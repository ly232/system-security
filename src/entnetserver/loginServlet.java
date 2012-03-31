package entnetserver;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

//import sun.nio.cs.ext.DBCS_IBM_EBCDIC_Decoder;
import Constants.Constants;
import JDBC.DataBase;
import XML.XMLRequest;

public class loginServlet extends Servelet implements Runnable{

	public loginServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		String sqlQuery = "select aes_decrypt(user_id, 'cornell'),"
                        + "aes_decrypt(user_pwd,'cornell'),"
                        + "aes_decrypt(contact_info,'cornell'),"
                        + "aes_decrypt(role_id,'cornell')"
                        + " from user where aes_decrypt(user_id, 'cornell') = \"" + xmlRequest.getUserID() + 
				"\" and aes_decrypt(user_pwd,'cornell') = \"" + xmlRequest.getRequestDetail() + "\";";
		//String k_db_str = this.handle.getKdb().skey.toString();
                
                System.out.println(sqlQuery);
                
                //String sqlQuery = "select AES_DECRYPT(user_id, k_db_str) from user U where U.";
            
		DataBase dB =  handle.getSysDB();
		ResultSet rSet = dB.DoQuery(sqlQuery);
		try {
			if (rSet.first()) {
				xmlRequest.setRequestDetail(Constants.TRUE);
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
