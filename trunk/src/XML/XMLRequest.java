package XML;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.server.UID;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Security.MyKey;
import Security.MyPKI;
import Security.SharedKey;

import Constants.Constants;

import Security.*;

public class XMLRequest implements Serializable{
		static UID uid;
		public static MyKey sessionKey;
		String requestID;
		String userID;
		String regionID;
		String sessionID;//for the later use
		String requestDetail;
		String actionID;
		MyResultSet myResultSet;

		public MyResultSet getMyResultSet() {
			return myResultSet;
		}

		public void setMyResultSet(MyResultSet myResultSet) {
			this.myResultSet = myResultSet;
		}


		public String getRequestID() {
			return requestID;
		}

		public void setRequestID(String requestID) {
			this.requestID = requestID;
		}

		public String getUserID() {
			return userID;
		}

		public void setUserID(String userID) {
			this.userID = userID;
		}

		public String getRegionID() {
			return regionID;
		}

		public void setRegionID(String regionID) {
			this.regionID = regionID;
		}

		public String getSessionID() {
			return sessionID;
		}

		public void setSessionID(String sessionID) {
			this.sessionID = sessionID;
		}

		public String getRequestDetail() {
			return requestDetail;
		}

		public void setRequestDetail(String requestDetail) {
			this.requestDetail = requestDetail;
		}

		public String getActionID() {
			return actionID;
		}

		public void setActionID(String actionID) {
			this.actionID = actionID;
		}
		
		void generateSessionKey(){
			if (uid == null) {
				//get a sessionKey
				uid = new UID();
				SharedKey sk = SharedKey.getInstance();
				sessionKey = sk.generateKeyWithPwd(uid.toString());
			}
		}
		
		/**
		 * @param requestID string in Constants
		 * @param userID string
		 * @param regionID String in Constants
		 * @param sessionID String
		 * @param requestDetail String client is sqlQuery
		 * @param ActionID String in Constants
		 */
		public XMLRequest(
                        String requestID,	
                        String userID,
                        String regionID,			 
                        String sessionID,
                        String requestDetail,
                        String ActionID) 
                {
			 this.requestID = requestID;
			this.userID = userID;
			this.regionID = regionID;
			this.sessionID = sessionID;//for the later use
			this.requestDetail = requestDetail;
			this.actionID = ActionID;
			//TODO: generate a sessionKey
			//generateSessionKey();
			//encrypt();
		}
		
		
		public void encrypt(){
				if (requestID == Constants.SESSION_KEY_EST) {
					return;
				}
				SharedKey sk  = SharedKey.getInstance();
				this.userID = new String(sk.encrypt(userID, sessionKey));
				this.regionID = new String(sk.encrypt(regionID, sessionKey));
				this.sessionID = new String(sk.encrypt(sessionID, sessionKey));//for the later use
				this.requestDetail = new String(sk.encrypt(requestDetail, sessionKey));
				this.actionID = new String(sk.encrypt(actionID, sessionKey));
		}
		
		public void decrypt(String pwd){
				SharedKey sk  = SharedKey.getInstance();
				//String  = sk.decrypt(requestID.getBytes(),sessionKey);
				this.userID = sk.decrypt(userID.getBytes(), sessionKey);
				this.regionID = sk.decrypt(regionID.getBytes(), sessionKey);
				this.sessionID = sk.decrypt(sessionID.getBytes(), sessionKey);//for the later use
				this.requestDetail = sk.decrypt(requestDetail.getBytes(), sessionKey);
				this.actionID = sk.decrypt(actionID.getBytes(), sessionKey);
		}
		
		
	   /**
	    * initialize using a xml file
	 * @param xmlString
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public XMLRequest(String xmlString) throws ParserConfigurationException, SAXException, IOException {
		   this.ParseXML(xmlString);
		   //generateSessionKey();
			//encrypt();
		}
		
		/**
		 * get the XML file of this request
		 * @return
		 */
		public String generateXMLRequest(){
			String xmlString = new String();
			XML_creator_API xmlnew = new XML_creator_API();
			xmlnew.createRoot(requestID);
			xmlnew.createChild("userID", requestID);
			xmlnew.createText(userID, "userID");
			xmlnew.createChild("regionID", requestID);
			xmlnew.createText(regionID, "regionID");
			xmlnew.createChild("requestDetail", requestID);
			xmlnew.createText(requestDetail, "requestDetail");
			xmlnew.createChild("actionID", requestID);
			xmlnew.createText(actionID, "actionID");
			xmlString = xmlnew.getXMLstring();
			return xmlString;
		}
		
		public void ParseXML(String xmlString) throws ParserConfigurationException, SAXException, IOException {
				XML_parser_API xmlNewApi = new XML_parser_API(xmlString);
				requestID = xmlNewApi.getRootTagName();
				userID = xmlNewApi.getTagValue("userID");
				regionID = xmlNewApi.getTagValue("regionID");
				//this.sessionID = sessionID;//for the later use
				requestDetail = xmlNewApi.getTagValue("requestDetail");
				actionID = xmlNewApi.getTagValue("actionID");
		}
}
