package JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DataBaseTest extends TestCase {
	String url = "jdbc:mysql://localhost:3306/security";
	String username = "root";
	String password = "mysql";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDataBase() {
		try {
			DataBase db = new DataBase(url, username, password);
			assertTrue(db.connection == null);
		} catch (Exception e) {
			fail("create error");
		}


	}

	public void testInitialize() {
		DataBase db = new DataBase(url, username, password);
		db.initialize();
		assertTrue(db.connection != null);
	}

	public void testDoQuery() {
		DataBase db = new DataBase(url, username, password);
		db.initialize();
		db.DoQuery("select * from user;");
		try {
			ResultSet rs = db.lastRs;
			System.out.println("Display all results:");
	   	 	while(rs.next()){
	   	 		int theInt= rs.getInt("id");
	   	 		String nameStr = rs.getString("username");
	   	 		String passwordStr = rs.getString("password");
	   	 		System.out.println("id= " + theInt
	   	 				+ "  name = " + nameStr + "  password = " + passwordStr);
	   	 	}
	   	 	rs.first();
	   	 	assertEquals(rs.getInt("id"), 1);
	   	 	assertEquals(rs.getString("username"), "tao");
	   	 	assertEquals(rs.getString("password"), "123");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
			fail("SQLfail");
		}
	}
	
	public void testFinish() {
		try {
			DataBase db = new DataBase(url, username, password);
			db.initialize();
			db.DoQuery("select * from user;");
			db.finish();
			assertTrue(db.connection.isClosed());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
			fail("SQLfail");
		}
	}
	
}
