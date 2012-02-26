package entnetserver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import XML.XML_creator_API;

import com.sun.rowset.*;

import javax.sql.rowset.WebRowSet;

import Constants.*;
import JDBC.*;

public class ReadServelet implements Runnable{
	String sqlQuery;
	OutputStream outputStream;
	ThreadedHandler handle;
	int requestID;
	public ReadServelet(Integer request, String sqlString,ThreadedHandler th) {
		sqlQuery = sqlString;
		this.handle = th;
		this.requestID = request;
	}
	
	public void run() {
		try {
       	 	DataBase db = handle.getSysDB();
			db.initialize();
			ResultSet rs = db.DoQuery(sqlQuery);
 			WebRowSet wrs = new WebRowSetImpl();
 			wrs.populate(rs);
 			ByteArrayOutputStream bos = new ByteArrayOutputStream();
 			wrs.writeXml(bos);
 			String result = new String(bos.toByteArray(),"utf-8");
 			XML_creator_API xmlNew = new XML_creator_API();
 			handle.callBackResult(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
