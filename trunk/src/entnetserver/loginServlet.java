package entnetserver;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import sun.nio.cs.ext.DBCS_IBM_EBCDIC_Decoder;
import JDBC.DataBase;
import XML.XMLRequest;

public class loginServlet extends Servelet implements Runnable{

	public loginServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		String sqlQuery = "select * from entnetdb_v2.user U where U.user_id = " + xmlRequest.getUserID() + 
				" AND U.user_pwd = " + xmlRequest.getRequestDetail() + ";";
		DataBase dB =  handle.getSysDB();
		ResultSet rSet = dB.DoQuery(sqlQuery);
		try {
			if (rSet.first()) {
				xmlRequest.setRequestDetail("true");
			}else {
				xmlRequest.setRequestDetail("false");
			}
			handle.callBackResult(xmlRequest);
		} catch (SQLException e) {
			System.out.println("SQLfault at login");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("call Back fault");
			e.printStackTrace();
		}
	}

	
	
	
}
