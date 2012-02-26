package XML;

public class XMLRequest {
		String requestID;
		String userID;
		String regionID;
		String sessionID;//for the later use
		String requestDetail;
		String actionID;
		
		public XMLRequest(String requestID,	String userID,String regionID,
										 String sessionID,String requestDetail,String ActionID) {
			 this.requestID = requestID;
			this.userID = userID;
			this.regionID = regionID;
			this.sessionID = sessionID;//for the later use
			this.requestDetail = requestDetail;
			this.actionID = ActionID;
		}
		
		public String generateXMLRequest(){
			String xmlString = new String();
			XML_creator_API xmlnew = new XML_creator_API();
			xmlnew.createRoot(requestID);
			xmlnew.createChild("userID", requestID);
			xmlnew
			xmlnew.createChild("regionID", requestID);
			xmlnew.createChild("requestDetail", requestID);
			xmlnew.createChild("actionID", actionID);
			xmlnew.createChild("requestDetail", requestDetail);
			return xmlString;
		}
		
		
		
		
}
