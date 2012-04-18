package entnetserver;

import java.sql.SQLException;

import Constants.Constants;
import JDBC.DataBase;
import Security.SharedKey;
import XML.XMLRequest;

public class UpdateServlet extends Servelet implements Runnable{

	public UpdateServlet(XMLRequest rq, ThreadedHandler th) {
		super(rq, th);
	}

	public void run() {
		try {
       	 	DataBase db = handle.getSysDB();
			db.initialize();
			String query = null;
			SharedKey sk = SharedKey.getInstance();
			if (this.xmlRequest.getRequestID().equals(Constants.REGIST_REQUEST_ID)){
				//System.out.println("updateServelet: get a regist reqest");
				
				String regist_uname = 
					new String(sk.sessionKeyDecrypt(handle.k_session, 
							xmlRequest.requestData.get("user_id")));
				String regist_pwd = 
					new String(sk.sessionKeyDecrypt(handle.k_session, 
							xmlRequest.requestData.get("password")));
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
				System.out.println("addfriendquery = "+query);
				
			}
			else if (this.xmlRequest.getRequestID().equals(Constants.UPDATE_REGION_ID)){
				if (this.xmlRequest.getRequestDetail().equals(Constants.POST_FRIEND_MESSAGE)){
					String friend_id = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("postedFriendID")));
					String messageString = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("postedFriendMessage")));
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
					query = 
						"UPDATE currloc SET loc_id = aes_encrypt('" + newContent 
	                    + "','"+ThreadedHandler.db_pwd+"') WHERE aes_decrypt(user_id,'"
	                    +ThreadedHandler.db_pwd+"') = '" 
	                    + xmlRequest.getUserID() + "';";					
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.REGION3)){
					String newContent = 
						new String(sk.sessionKeyDecrypt(handle.k_session,
								this.xmlRequest.requestData.get("newContent")));
					query = 
						"UPDATE workon SET pid = aes_encrypt('" + newContent 
	                    + "','"+ThreadedHandler.db_pwd+"') WHERE aes_decrypt(user_id,'"
	                    +ThreadedHandler.db_pwd+"') = '" 
	                    + xmlRequest.getUserID() + "';";	
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.REGION4)){
					//TODO: add authorization check here...only boss (roleID=1) can update region 4 (company message)
				}
				else if (this.xmlRequest.getRequestDetail().equals(Constants.REGION5)){
					//TODO: add authorization check here...only dept_head (roleID=2) can update region 5 (dept message)
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
