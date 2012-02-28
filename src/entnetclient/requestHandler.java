package entnetclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Constants.Constants;
import XML.MyResultSet;
import XML.XMLRequest;

public class requestHandler implements Runnable{
	
		private static Socket socket;
		private XMLRequest xmlRequest;
		private EntNetClient handleClient;
		public boolean setXML = false;
		private XMLRequest testRequest;
		
		
		public XMLRequest getTestRequest() {
			return testRequest;
		}

		public void setTestRequest(XMLRequest testRequest) {
			this.testRequest = testRequest;
		}

		//private
		public requestHandler(XMLRequest rq, EntNetClient enc) {
			xmlRequest = rq;
			handleClient = enc;
			if (socket == null) {
				try {
					socket = new Socket("localhost",8189);
					System.out.println("Client Socket initialized, connect with server");
				} catch (UnknownHostException e) {
					    System.out.println("Socket initialize fail");
					e.printStackTrace();
				} catch (IOException e) {
						System.out.println("socket initialize fail");
					e.printStackTrace();
				}
			}
			
		}
		
		public XMLRequest getXmlRequest() {
			return xmlRequest;
		}


		public void setXmlRequest(XMLRequest xmlRequest) {
			this.xmlRequest = xmlRequest;
		}
		
		
		@Override
		public void run() {
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				// autoflush
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
	            out.println(xmlRequest.generateXMLRequest());
	            
	            String resultString = new String();
	            String onelineString = new String();
	            while ((onelineString = in.readLine()).equals(Constants.END_STRING) == false) {
	            	resultString += onelineString;
	            }
	            XMLRequest resultRequest  = new XMLRequest(resultString);
	            if (resultRequest.getRequestDetail().equals(Constants.RETURN_RESULTSET) ){
	            	InputStream o = socket.getInputStream();
	            	ObjectInput s = new ObjectInputStream(o);
	            	//ois = new ObjectInputStream(socket.getInputStream());
	            	MyResultSet mrs = (MyResultSet) s.readObject();
	            	resultRequest.setMyResultSet(mrs);
				}
	            testRequest = resultRequest;
	            setXML = true;
	            handleClient.requestThreadCallBack(resultRequest);
			} catch (IOException e) {
				System.out.println("socket error");
				System.err.println(e.getLocalizedMessage());
				e.printStackTrace();
			} // true means
 catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		
		
}
