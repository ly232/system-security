package entnetserver;

import java.sql.ResultSet;
import java.sql.SQLException;

import Constants.Constants;
import JDBC.DataBase;
import Security.SharedKey;
import XML.MyResultSet;
import XML.XMLRequest;

public class UpdateServlet extends Servelet implements Runnable{

	public UpdateServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
	}	
	
	
	String getDid(DataBase db){
		String Query = "select aes_decrypt(deptID_workat, 'cornell'),"
                + " from workat where aes_decrypt(userID_workat, 'cornell') = \"" + handle.getUserID() + "\";";
		ResultSet rSet =  db.DoQuery(Query);
		try {
			if (rSet.first()) {
				return rSet.getString("deptID_workat");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "1";
	}
	
	
	public void run() {
		try {
       	 	DataBase db = handle.getSysDB();
			db.initialize();
			String query = null;
			SharedKey sk = SharedKey.getInstance();
			
			//TODO: registration can only be done by the sys admin
			//this means the sys admin provides k_db at the client side
			//server will generate sql with k_db provided by sys admin.
			if (this.xmlRequest.getRequestID().equals(Constants.REGIST_REQUEST_ID)){
				String vcode = new String(sk.sessionKeyDecrypt(handle.k_session, 
						xmlRequest.requestData.get("vcode")));
				if (vcode.equals(ThreadedHandler.db_pwd)==false){
					xmlRequest.setRequestDetail(Constants.INVALID_VCODE);
					handle.callBackResult(xmlRequest);
					return;
				}
				
				String regist_uname = 
					new String(sk.sessionKeyDecrypt(handle.k_session, 
							xmlRequest.requestData.get("user_id")));
				String regist_pwd = 
					new String(sk.sessionKeyDecrypt(handle.k_session, 
							xmlRequest.requestData.get("password")));
				if(CheckPassword.checkPassword(regist_pwd)==false){
					System.out.println("UpdateServlet: invalid password for registration");
					xmlRequest.setRequestDetail(Constants.INVALID_PASSWORD_SETUP);
					handle.callBackResult(xmlRequest);
					return;
				}
				
				
				String regist_contact_info = 
					new String(sk.sessionKeyDecrypt(handle.k_session, 
							xmlRequest.requestData.get("contact_info")));
				String regist_role_id = 
					new String(sk.sessionKeyDecrypt(handle.k_session, 
							xmlRequest.requestData.get("role_id")));
				

				query = "INSERT INTO user (user_id, user_pwd, contact_info, role_id) VALUES ("
	                + "AES_ENCRYPT('"+regist_uname+"','"+handle.getDBpwd()+"'), "
	                + "AES_ENCRYPT('"+regist_pwd+"','"+handle.getDBpwd()+"'), "
	                + "AES_ENCRYPT('"+regist_contact_info+"','"+handle.getDBpwd()+"'), "
	                + "AES_ENCRYPT('"+regist_role_id+"','"+handle.getDBpwd()+"'));";
				
			}
			else if (this.xmlRequest.getRequestID().equals(Constants.DELETE_FRIEND_ID)){
				//first, check that the invoker is indeed the user
				if (handle.getUserID().equals(xmlRequest.getUserID())==false){
					System.err.println("ACCESS DENIED: in UpdateServlet::delete_friend, invoker and source uid doesnt match!");
					xmlRequest.setRequestDetail(Constants.ACCESS_DENIED);
		 			handle.callBackResult(xmlRequest);
		 			return;
				}
				String deleteFriendID = 
					new String(sk.sessionKeyDecrypt(handle.k_session,
							this.xmlRequest.requestData.get("deleteFriendID")));
				query = 
					"DELETE FROM friend where (aes_decrypt(user1,'"
					+ThreadedHandler.db_pwd+"') " +
					"= \"" + xmlRequest.getUserID() 
					+"\" AND aes_decrypt(user2,'"+ThreadedHandler.db_pwd
					+"') = \"" + deleteFriendID + "\")" +
							" or (aes_decrypt(user2,'"
					+ThreadedHandler.db_pwd+"') " +
					"= \"" + xmlRequest.getUserID() 
					+"\" AND aes_decrypt(user1,'"+ThreadedHandler.db_pwd
					+"') = \"" + deleteFriendID + "\");";
			}
			else if (this.xmlRequest.getRequestID().equals(Constants.ADD_FRIEND_ID)){
				//first, check that the invoker is indeed the user
				if (handle.getUserID().equals(xmlRequest.getUserID())==false){
					System.err.println("ACCESS DENIED: in UpdateServlet::add_friend, invoker and source uid doesnt match!");
					xmlRequest.setRequestDetail(Constants.ACCESS_DENIED);
		 			handle.callBackResult(xmlRequest);
		 			return;
				}
				
				String requestFriendID = 
					new String(sk.sessionKeyDecrypt(handle.k_session,
							this.xmlRequest.requestData.get("requestFriendID")));
				query = 
					"insert into friend " +
	      	  		"(user1, user2, message, msg_id) " +
	      	  		"values(aes_encrypt('"+xmlRequest.getUserID()+"','"+ThreadedHandler.db_pwd
	      	  		+"'), aes_encrypt('"+requestFriendID+"','"+ThreadedHandler.db_pwd
	      	  		+"') , aes_encrypt('"+Constants.FRIEND_REQUEST+"','"
	      	  		+ThreadedHandler.db_pwd+"'),  " +
	      	  				"aes_encrypt('0','"+ThreadedHandler.db_pwd+"'));";
				//System.out.println("addfriendquery = "+query);
				
			}
			else if (this.xmlRequest.getRequestID().equals(Constants.UPDATE_REGION_ID)){
				//first, check that the invoker is indeed the user
				if (handle.getUserID().equals(xmlRequest.getUserID())==false){
					System.err.println("ACCESS DENIED: in UpdateServlet::update_region, invoker and source uid doesnt match!");
					xmlRequest.setRequestDetail(Constants.ACCESS_DENIED);
		 			handle.callBackResult(xmlRequest);
		 			return;
				}
				
				if (this.xmlRequest.getRequestDetail().equals(Constants.POST_FRIEND_MESSAGE)){
					String friend_id = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("postedFriendID")));
					String messageString = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("postedFriendMessage")));
					//check msg integrity:
					Boolean integrity = SharedKey.checkHash(messageString, xmlRequest.getSessionID());
					if (!integrity){
						System.err.println("UpdateServlet: integrity checking failed for post friend msg");
						xmlRequest.setRequestDetail(Constants.INTEGRITY_VIOLATION);
						handle.callBackResult(xmlRequest);
						return;
					}
					
					query = 
						"insert into friend values(aes_encrypt('" + friend_id 
						+ "', '"+ThreadedHandler.db_pwd+"'),aes_encrypt('" 
						+ xmlRequest.getUserID() + 
						"','"+ThreadedHandler.db_pwd+"'),aes_encrypt('" 
						+ messageString + "','"+ThreadedHandler.db_pwd
						+"'),aes_encrypt('1','"+ThreadedHandler.db_pwd+"'));";
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.REGION1)){
					String newContent = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("newContent")));
					query = 
						"UPDATE user SET contact_info = aes_encrypt('" + newContent 
	                    + "','"+ThreadedHandler.db_pwd+"') WHERE aes_decrypt(user_id,'"
	                    +ThreadedHandler.db_pwd+"') = '" 
	                    + xmlRequest.getUserID() + "';";
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.REGION2)){
					String newContent = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("newContent")));
					String newLocID_query = "select aes_decrypt(loc_id, '"
						+ThreadedHandler.db_pwd+"') as loc_id from location where aes_decrypt(loc_name,'"
						+ThreadedHandler.db_pwd+"')='"+newContent+"';";
					ResultSet rs = db.DoQuery(newLocID_query);
					rs.first();
					String newLocID = rs.getString("loc_id");
					
					
					query = 
						"UPDATE currloc SET loc_id = aes_encrypt('" + newLocID 
	                    + "','"+ThreadedHandler.db_pwd+"') WHERE aes_decrypt(user_id,'"
	                    +ThreadedHandler.db_pwd+"') = '" 
	                    + xmlRequest.getUserID() + "';";		
					
					System.out.println("location update query = "+query);
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.REGION3)){
					String newContent = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("newContent")));
					String newProjID_query = "select aes_decrypt(proj_id,'"
						+ThreadedHandler.db_pwd+"') as proj_id from project where aes_decrypt(proj_name,'"
						+ThreadedHandler.db_pwd+"')='"+newContent+"';";
					//System.out.println("newProjID_query: "+newProjID_query);
					
					ResultSet rs = db.DoQuery(newProjID_query);
					rs.first();
					String newProjID = rs.getString("proj_id");
										
					query = 
						"UPDATE workon SET pid = aes_encrypt('" + newProjID 
	                    + "','"+ThreadedHandler.db_pwd+"') WHERE aes_decrypt(uid,'"
	                    +ThreadedHandler.db_pwd+"') = '" 
	                    + xmlRequest.getUserID() + "';";
					
					//System.out.println("query = " + query);
					
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.POST_COMPANY_MESSAGE)){ //post company message

					//TODO: add authorization check here...only boss (roleID=1) can update region 4 (company message)
					//ACCESS CONTROL: check with database that the roleID indicates that the xmlRequest is from the boss:
					String roleCheckQuery = "select aes_decrypt(role_id,'"
						+ThreadedHandler.db_pwd+"') as role_id from user where aes_decrypt(user_id,'"+ThreadedHandler.db_pwd
						+"')='"+xmlRequest.getUserID()+"';";
					ResultSet rs = db.DoQuery(roleCheckQuery);
					rs.first();
					String this_user_role_id = rs.getString("role_id");
					if (this_user_role_id.equals("1")){ //role id is the boss. continue to post the company message.
						//System.out.println("hello boss");
						String postedMsg = 
							new String(sk.sessionKeyDecrypt(handle.k_session,
									this.xmlRequest.requestData.get("postedCompnayMessage")));
						Boolean integrity = SharedKey.checkHash(postedMsg, xmlRequest.getSessionID()); //we stored hash value in session id field...if time allows we will rename this field.
						if (!integrity){
							System.err.println("UpdateServlet: integrity violation for post company msg");
							xmlRequest.setRequestDetail(Constants.INTEGRITY_VIOLATION);
				 			handle.callBackResult(xmlRequest);
							return;
						}
							
						//System.out.println("postedMsg="+postedMsg);
						query = 
							"insert into postworkmessage (msg_id,did,msg_content) values " +
									"(aes_encrypt('1','"+ThreadedHandler.db_pwd+"')," +
									"aes_encrypt('"+Constants.COMPANY_DID+"','"+ThreadedHandler.db_pwd
									+"'),aes_encrypt('"+postedMsg+"','"+ThreadedHandler.db_pwd+"'));";
						//System.out.println("company board update query = "+query);
					}
					else{ //the role id does not match a boss. access denied.
						System.err.println("ACCESS DENIED: in UpdateServlet::update_region, invoker tries to update company board but role id is not boss!");
						xmlRequest.setRequestDetail(Constants.ACCESS_DENIED);
			 			handle.callBackResult(xmlRequest);
			 			return;
						
					}
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.POST_DEPT_MESSAGE)){ //post department message

					//TODO: add authorization check here...only dept_head (roleID=2) can update region 5 (dept message)
					//ACCESS CONTROL: check with database that the roleID indicates that the xmlRequest is from the boss:
					
					String roleCheckQuery = "select aes_decrypt(role_id,'"
						+ThreadedHandler.db_pwd+"') as role_id from user where aes_decrypt(user_id,'"+ThreadedHandler.db_pwd
						+"')='"+xmlRequest.getUserID()+"';";
					ResultSet rs = db.DoQuery(roleCheckQuery);
					rs.first();
					String this_user_role_id = rs.getString("role_id");
					if (this_user_role_id.equals("2")){ //role id is the dept head. continue to post the company message.
						String postedMsg = 
							new String(sk.sessionKeyDecrypt(handle.k_session,
									this.xmlRequest.requestData.get("postedDeptMessage")));
		
						Boolean integrity = SharedKey.checkHash(postedMsg, xmlRequest.getSessionID()); //we stored hash value in session id field...if time allows we will rename this field.
						if (!integrity){
							System.err.println("UpdateServlet: integrity violation for post department msg");
							xmlRequest.setRequestDetail(Constants.INTEGRITY_VIOLATION);
				 			handle.callBackResult(xmlRequest);
							return;
						}
						
						
						String deptID_query = "select aes_decrypt(deptID_workat,'"
							+ThreadedHandler.db_pwd+"') as deptID_workat from workat where aes_decrypt(userID_workat,'"
							+ThreadedHandler.db_pwd+"')='"+xmlRequest.getUserID()+"';";
						rs = db.DoQuery(deptID_query);
						rs.first();
						String deptID = rs.getString("deptID_workat");
						
						query = 
							"insert into postworkmessage (msg_id,did,msg_content) values " +
									"(aes_encrypt('1','"+ThreadedHandler.db_pwd+"')," +
									"aes_encrypt('"+deptID+"','"+ThreadedHandler.db_pwd
									+"'),aes_encrypt('"+postedMsg+"','"+ThreadedHandler.db_pwd+"'));";
					}
					else{ //the role id does not match a boss. access denied.
						System.err.println("ACCESS DENIED: in UpdateServlet::update_region, invoker tries to update company board but role id is not boss!");
						xmlRequest.setRequestDetail(Constants.ACCESS_DENIED);
			 			handle.callBackResult(xmlRequest);
			 			return;
						
					}
					
				}
				else{
					System.err.println("UpdateServlet: unsupported xmlrequestdetail under updateregionid");
				}
			}
			
			int retureCount = db.DoUpdateQuery(query);
			xmlRequest.setRequestDetail(String.format("%d", retureCount));
 			handle.callBackResult(xmlRequest);

		} catch (Exception e) {
			
		}

	}
}
