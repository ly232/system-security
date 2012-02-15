package JDBC;
import java.security.interfaces.RSAKey;
import java.sql.*;

import javax.print.attribute.standard.Finishings;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class DataBase {
	String url;
	String username;
	String password;
	Connection connection;
	public ResultSet lastRs;
	
	
	 public DataBase(String url,String username, String password){
		 this.url = url;
		 this.username = username;
		 this.password = password;
		 connection = null;
		 lastRs = null;
	 }
	 
	 public void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url,username,password);
            connection = conn;
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	 
	 public ResultSet DoQuery(String query) {
		try {
			 if (connection.isClosed() == true) {
					System.out.println("initial first");
					return null;
				}
			ResultSet rs;
			Statement stmt;
        	stmt = connection.createStatement();
        	//rs = stmt.executeQuery("select * from user;");
        	rs = stmt.executeQuery(query);
        	lastRs = rs;
         	return rs;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
	}
	 
	 public void finish(){
		 try {
			connection.close();
			lastRs = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		connection.close();
	}
}
