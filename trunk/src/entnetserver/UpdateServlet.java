package entnetserver;

import java.sql.SQLException;

import JDBC.DataBase;
import XML.XMLRequest;

public class UpdateServlet extends Servelet implements Runnable{

	public UpdateServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
	}

	public void run() {
		try {
       	 	DataBase db = handle.getSysDB();
			db.initialize();
			int retureCount = db.DoUpdateQuery(xmlRequest.getRequestDetail());
			xmlRequest.setRequestDetail(String.format("%d", retureCount));
 			handle.callBackResult(xmlRequest);
		} catch (Exception e) {
			
		}

	}
}
