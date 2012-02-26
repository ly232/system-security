package entnetserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.WebRowSet;

import com.sun.rowset.WebRowSetImpl;

import JDBC.DataBase;
import XML.XMLRequest;

public class ReadServlet extends Servelet implements Runnable{

	public ReadServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
	}

	public void run() {
		try {
       	 	DataBase db = handle.getSysDB();
			db.initialize();
			ResultSet rs = db.DoQuery(xmlRequest.getRequestDetail());
 			WebRowSet wrs = new WebRowSetImpl();
 			wrs.populate(rs);
 			ByteArrayOutputStream bos = new ByteArrayOutputStream();
 			wrs.writeXml(bos);
 			String result = new String(bos.toByteArray(),"UTF-8");
 			xmlRequest.setRequestDetail(result);
 			handle.callBackResult(xmlRequest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
