package XML;

public class XMLRequest {
		String requestID;
		String userID;
		String regionID;
		String sessionID;//for the later use
		String requestDetail;
		String actionID;
		
		/**
		 * @param requestID string in Constants
		 * @param userID string
		 * @param regionID String in Constants
		 * @param sessionID String
		 * @param requestDetail String client is sqlQuery
		 * @param ActionID String in Constants
		 */
		public XMLRequest(String requestID,	String userID,String regionID,
										 String sessionID,String requestDetail,String ActionID) {
			 this.requestID = requestID;
			this.userID = userID;
			this.regionID = regionID;
			this.sessionID = sessionID;//for the later use
			this.requestDetail = requestDetail;
			this.actionID = ActionID;
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
			xmlnew.createChild("actionID", actionID);
			xmlnew.createText(actionID, "actionID");
			xmlString = xmlnew.getXMLstring();
			return xmlString;
		}
		
		public void ParseXML(String xmlString) {
			XML_parser_API xmlNewApi = new XML_parser_API(xmlString);
			requestID = xmlNewApi.getRootTagName();
			userID = xmlNewApi.getTagValue("userID");
			regionID = xmlNewApi.getTagValue("regionID");
			//this.sessionID = sessionID;//for the later use
			requestDetail = xmlNewApi.getTagValue("requestDetail");
			actionID = xmlNewApi.getTagValue("actionID");
			return;
		}
}
