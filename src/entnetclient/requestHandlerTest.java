package entnetclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sql.rowset.WebRowSet;

//import com.sun.rowset.WebRowSetImpl;

import entnetserver.EntNetServer;
import entnetserver.loginServlet;
import Constants.Constants;
import XML.MyResultSet;
import XML.XMLRequest;
import junit.framework.TestCase;

public class requestHandlerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRequestHandler() {
		XMLRequest testRequest = null;
		MyResultSet mrs = null;
		Object[] rows = null;
		XMLRequest xmlRequest = new XMLRequest(Constants.READ_REGION_ID, "tao",
															Constants.REGION0, null, "select * from user;", Constants.SELECT);
		XMLRequest loginRequest = new XMLRequest(Constants.LOGIN_REQUEST_ID, "tao",
															Constants.REGION0, null, "123", Constants.SELECT);
		//test Login
		ClientMain testmain= new ClientMain();
		EntNetClient testEntNetClient = EntNetClient.getInstance(testmain);
		
		requestHandler hrHandler = new requestHandler(loginRequest,testEntNetClient);
		hrHandler.run();
		 testRequest = hrHandler.getTestRequest();
		assertEquals(testRequest.getRequestDetail(), Constants.TRUE);
		
		String myQuery = "INSERT INTO entnetdb_v2.user VALUES (\"shuai\",\"123\",\"607111111\",1);";
		XMLRequest regiRequest = new XMLRequest(Constants.UPDATE_REGION_ID, "tao",
															Constants.REGION0, null, myQuery, Constants.UPDATE);
		requestHandler hr2 = new requestHandler(regiRequest,testEntNetClient);
		hr2.run();
		testRequest = hr2.getTestRequest();
		System.out.println(testRequest.getRequestDetail());
		assertEquals(testRequest.getRequestDetail(), "1");
		
		
		String q3 = "DELETE FROM entnetdb_v2.user WHERE user_id = \"shuai\";";
		XMLRequest rq3 = new XMLRequest(Constants.UPDATE_REGION_ID, "tao",
															Constants.REGION0, null, q3, Constants.UPDATE);
		requestHandler hr3 = new requestHandler(rq3,testEntNetClient);
		hr3.run();
		testRequest = hr3.getTestRequest();
		assertEquals(testRequest.getRequestDetail(), "1");
		
		String q4 = "select * from entnetdb_v2.user U where U.user_id = \"tao\";";
		XMLRequest rq4 = new XMLRequest(Constants.READ_REGION_ID, "tao",
															Constants.REGION1, null, q4, Constants.SELECT);
		requestHandler hr4 = new requestHandler(rq4,testEntNetClient);
		hr4.run();
		testRequest = hr4.getTestRequest();
		mrs = testRequest.getMyResultSet();
		rows = mrs.getTable().toArray();
		assertEquals(rows.length, 1);

		String q5 = "SELECT L.loc_name FROM entnetdb_v2.currloc C, entnetdb_v2.location L WHERE C.user_id = \"tao\" AND L.loc_id = C.loc_id;";
		XMLRequest rq5 = new XMLRequest(Constants.READ_REGION_ID, "tao",
															Constants.REGION0, null, q5, Constants.SELECT);
		requestHandler hr5 = new requestHandler(rq5,testEntNetClient);
		hr5.run();
		testRequest = hr5.getTestRequest();
		mrs = testRequest.getMyResultSet();
	    rows =  mrs.getTable().toArray();
		assertEquals(rows.length, 1);
		System.err.println(mrs.getStringValue(0, "loc_name"));
		assertEquals(mrs.getStringValue(0, "loc_name"),"ShangHai");
		
		String q6 = "insert into friend values(\"tao\",\"lin\",\"hello\",null);";
		XMLRequest rq6 = new XMLRequest(Constants.ADD_FRIEND_ID, "tao",
															Constants.REGION0, null, q6, Constants.UPDATE);
		requestHandler hr6 = new requestHandler(rq6,testEntNetClient);
		hr6.run();
		testRequest = hr6.getTestRequest();
		assertEquals(testRequest.getRequestDetail(), "1");
		//("Not yet implemented");
  	    
		String thisUserID = "tao";
		String friend_id = "lin";
		String messageString = "hello yin";
  	    String q7 = "insert into friend values(\"" + thisUserID + "\",\"" + friend_id + 
						"\",\"" + messageString + "\",null);";
		
  	   // String q7 = "insert into friend values(\"tao\",\"lin\",\"hello\",null);";
		XMLRequest rq7 = new XMLRequest(Constants.ADD_FRIEND_ID, "tao",
															Constants.REGION0, null, q7, Constants.UPDATE);
		requestHandler hr7 = new requestHandler(rq7,testEntNetClient);
		hr7.run();
		testRequest = hr7.getTestRequest();
		assertEquals(testRequest.getRequestDetail(), "1");
  	  
	}

	public void testRun() {
		//fail("Not yet implemented");
	}

}
