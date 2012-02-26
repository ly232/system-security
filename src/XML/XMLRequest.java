package XML;

public class XMLRequest {
		String requestID;
		String userID;
		String regionID;
		String sessionID;//for the later use
		String requestDetail;
		String ActionID;
		
		public XMLRequest(String requestID,	String userID,String regionID,
										 String sessionID,String requestDetail,String ActionID) {
			 this.requestID = requestID;
			this.userID = userID;
			this.regionID = regionID;
			this.sessionID = sessionID;//for the later use
			this.requestDetail = requestDetail;
			this.ActionID = ActionID;
		}
		
		public String generateXMLRequest(){
			String xmlString = new String();
			XML_creator_API xmlnew = new XML_creator_API();
			xmlnew.createRoot(requestID);
			xmlnew.createChild(userID, requestID);
			xmlnew.createChild(regionID, requestID);
			xmlnew.createChild("Detail", requestID);
			xmlnew.createChild(ActionID, regionID);
			return xmlString;
		}
		
}
