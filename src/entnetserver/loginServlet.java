package entnetserver;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import sun.nio.cs.ext.DBCS_IBM_EBCDIC_Decoder;
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
		String sqlQuery = "select * from entnetdb_v2.user U where U.user_id = \"" + xmlRequest.getUserID() + 
				"\" AND U.user_pwd = \"" + xmlRequest.getRequestDetail() + "\";";
		
		DataBase dB =  handle.getSysDB();
		ResultSet rSet = dB.DoQuery(sqlQuery);
		System.out.println(sqlQuery.equals(sqlQuery));
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
