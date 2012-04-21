package entnetserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sql.rowset.WebRowSet;

//import com.sun.rowset.WebRowSetImpl;

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
			String readQuery = "";
			if (this.xmlRequest.getRequestID().equals(Constants.READ_REGION_ID)){
				if (this.xmlRequest.getRegionID().equals(Constants.FRIENDLISTREGION)){
					readQuery = 
						"SELECT DISTINCT AES_DECRYPT(F.user2, '"+ThreadedHandler.db_pwd+"') as user2 FROM friend F WHERE "
						+ "AES_DECRYPT(F.user1,'"+ThreadedHandler.db_pwd+"') = '" + this.xmlRequest.getUserID() + "' "
						+ "AND AES_DECRYPT(F.user1,'"+ThreadedHandler.db_pwd+"') in ("
						+ "SELECT AES_DECRYPT(F2.user2,'"+ThreadedHandler.db_pwd+"') "
	                	+ "FROM friend F2 WHERE AES_DECRYPT(F2.user1,'"+ThreadedHandler.db_pwd+"')"
	                	+ "=AES_DECRYPT(F.user2,'"+ThreadedHandler.db_pwd+"'));";
					
					//System.out.println("friendlistrequestquery: "+readQuery);
					
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.NOTIFYREGION)){
					readQuery = 
						"SELECT DISTINCT aes_decrypt(F.user1,'"+ThreadedHandler.db_pwd+"') as user1 " +
								"FROM friend F WHERE aes_decrypt(F.user2,'"
								+ThreadedHandler.db_pwd+"') = '" 
								+ this.xmlRequest.getUserID() + "'"
								+ " AND aes_decrypt(F.user1,'"+ThreadedHandler.db_pwd
								+"') not in " 
								+"(SELECT aes_decrypt(F2.user2,'"+ThreadedHandler.db_pwd
								+"') FROM friend F2 WHERE aes_decrypt(F2.user1,'"+ThreadedHandler.db_pwd
								+"') = '" + this.xmlRequest.getUserID() + "' "
								+ "AND aes_decrypt(F2.user1,'"+ThreadedHandler.db_pwd+"') in ("
								+ "SELECT aes_decrypt(F3.user2,'"+ThreadedHandler.db_pwd
								+"') FROM friend F3 WHERE aes_decrypt(F3.user1,'"+ThreadedHandler.db_pwd
								+"')=aes_decrypt(F2.user2,'"+ThreadedHandler.db_pwd+"')));";
					//System.out.println("friend notification query: " + readQuery);
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.REGION1)){
					readQuery = 
						"SELECT AES_DECRYPT(user_id,'"+ThreadedHandler.db_pwd+"') as user_id, "
	                    + "AES_DECRYPT(contact_info,'"+ThreadedHandler.db_pwd+"') as contact_info "
	                    + "FROM user WHERE AES_DECRYPT(user_id,'"+ThreadedHandler.db_pwd+"') = '" 
	                    + this.xmlRequest.getUserID() + "';";
					//System.out.println("region1query = " + readQuery);
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.REGION2)){
					readQuery = 
						"SELECT AES_DECRYPT(loc_name,'"+ThreadedHandler.db_pwd
						+"') as loc_name FROM currloc NATURAL JOIN location"
	                    + " WHERE AES_DECRYPT(user_id,'"+ThreadedHandler.db_pwd
	                    +"') = '" 
	                    + this.xmlRequest.getUserID() 
	                    + "' AND AES_DECRYPT(currloc.loc_id,'"
	                    +ThreadedHandler.db_pwd+"') = AES_DECRYPT(location.loc_id,'"
	                    +ThreadedHandler.db_pwd+"');";
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.REGION3)){
					readQuery = 
						"SELECT AES_DECRYPT(proj_name,'"
						+ThreadedHandler.db_pwd+"') as proj_name FROM project NATURAL JOIN workon"
	                    + " WHERE AES_DECRYPT(uid,'"
	                    +ThreadedHandler.db_pwd+"') = '" + this.xmlRequest.getUserID()  
	                    + "' AND AES_DECRYPT(project.proj_id,'"+ThreadedHandler.db_pwd+"') = AES_DECRYPT(workon.pid,'"
	                    +ThreadedHandler.db_pwd+"');";
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.REGION4)){
					readQuery = 
						"SELECT AES_DECRYPT(msg_id,'"+ThreadedHandler.db_pwd+"') as msg_id, "
	                    + "AES_DECRYPT(msg_content,'"+ThreadedHandler.db_pwd+"') as msg_content FROM postworkmessage"
	                    + " WHERE AES_DECRYPT(did,'"+ThreadedHandler.db_pwd+"') = " + Constants.COMPANY_DID + ";";
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.REGION5)){
					readQuery = 
						"SELECT AES_DECRYPT(msg_id,'"+ThreadedHandler.db_pwd
						+"') as msg_id, "
	                    + "AES_DECRYPT(msg_content,'"+ThreadedHandler.db_pwd
	                    +"') as msg_content FROM postworkmessage NATURAL JOIN user "
	                    + "NATURAL JOIN workat"
	                    + " WHERE AES_DECRYPT(user.user_id,'"
	                    +ThreadedHandler.db_pwd+"') = AES_DECRYPT(workat.userID_workat,'"
	                    +ThreadedHandler.db_pwd+"')"
	                    + " AND AES_DECRYPT(workat.deptID_workat,'"
	                    +ThreadedHandler.db_pwd+"') = AES_DECRYPT(postworkmessage.did,'"
	                    +ThreadedHandler.db_pwd+"')"
	                    + " AND AES_DECRYPT(user.user_id,'"
	                    +ThreadedHandler.db_pwd+"') = '" 
	                    + this.xmlRequest.getUserID() + "';";
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.REGION6)){
					readQuery = 
						"SELECT aes_decrypt(user1,'"+ThreadedHandler.db_pwd
		            	+"') as user1, aes_decrypt(user2,'"
		            	+ThreadedHandler.db_pwd+"') as user2, aes_decrypt(message,'"
		            	+ThreadedHandler.db_pwd+"') as message, aes_decrypt(msg_id,'"
		            	+ThreadedHandler.db_pwd+"') as msg_id FROM friend WHERE aes_decrypt(user1,'"
		            	+ThreadedHandler.db_pwd+"') = '" 
		            	+ this.xmlRequest.getUserID()
		            	+ "' and aes_decrypt(message,'"+ThreadedHandler.db_pwd+"')" +
		            			"<>'"+Constants.FRIEND_REQUEST+"';";
					
					//System.out.println("read friendpost query = "+readQuery);
					
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.VALID_LOCATION)){
					readQuery = 
						"select aes_decrypt(loc_name,'"+ThreadedHandler.db_pwd
						+"') as loc_name from location;";
					
					//System.out.println("get valid location query = "+readQuery);
					
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.VALID_PROJECT)){
					readQuery = 
						"select aes_decrypt(proj_name,'"+ThreadedHandler.db_pwd
						+"') as proj_name from project;";
					
					//System.out.println("get valid project query = "+readQuery);
					
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.VALID_DEPT)){
					readQuery = 
						"select aes_decrypt(dname,'"+ThreadedHandler.db_pwd
						+"') as dname from department where aes_decrypt(dname,'"+ThreadedHandler.db_pwd+"')<>'boss';";
					
					//System.out.println("get valid project query = "+readQuery);
				}
				else if (this.xmlRequest.getRegionID().equals(Constants.SWITCH_DEPT)){
					String getDID_query = "select aes_decrypt(did,'"
						+ThreadedHandler.db_pwd+"') as did from department where aes_decrypt(dname,'"
						+ThreadedHandler.db_pwd+"')='"+xmlRequest.getRequestDetail()+"';";
					ResultSet rs = db.DoQuery(getDID_query);
					rs.first();
					String did = rs.getString("did");
					readQuery = 
						"select aes_decrypt(msg_content,'"
						+ThreadedHandler.db_pwd+"') as msg_content from postworkmessage where aes_decrypt(did,'"
						+ThreadedHandler.db_pwd+"')='"+did+"';";
					System.out.println("switch board query = "+readQuery);
				}
				else{
					System.err.println("ReadServlet: unrecognized regionID to read");
				}
			}

			else{
				System.out.println("ReadServlet: received a request that's not read region");
			}

			MyResultSet rs = new MyResultSet(db.DoQuery(readQuery), handle.k_session);

 			xmlRequest.setRequestDetail(Constants.RETURN_RESULTSET);
 			xmlRequest.setMyResultSet(rs);
 			handle.callBackResult(xmlRequest);
		}  catch (SQLException e) {
			xmlRequest.setRequestDetail(Constants.INVALID_REQUEST);
			handle.callBackResult(xmlRequest);
			System.err.println("SQL FAIL");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
