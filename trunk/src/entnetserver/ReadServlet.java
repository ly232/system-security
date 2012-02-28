package entnetserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.WebRowSet;

import com.sun.rowset.WebRowSetImpl;

import Constants.Constants;
import JDBC.DataBase;
import XML.MyResultSet;
import XML.XMLRequest;

public class ReadServlet extends Servelet implements Runnable{

	public ReadServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
	}

	public void run() {
		try {
       	 	DataBase db = handle.getSysDB();
			db.initialize();
			MyResultSet rs = new MyResultSet(db.DoQuery(xmlRequest.getRequestDetail()));
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//ObjectOutputStream oStream = new ObjectOutputStream(baos);
			//oStream.writeObject(rs);
			//String result = new String(baos.toByteArray(),"UTF-8");
 			xmlRequest.setRequestDetail(Constants.RETURN_RESULTSET);
 			//xmlRequest.setMyResultSeStringt(result);
 			xmlRequest.setMyResultSet(rs);
 			handle.callBackResult(xmlRequest);
		}  catch (SQLException e) {
			xmlRequest.setRequestDetail(Constants.INVALID_REQUEST);
			handle.callBackResult(xmlRequest);
			System.err.println("SQL FAIL");
			e.printStackTrace();
		}
		
	}
	
}
