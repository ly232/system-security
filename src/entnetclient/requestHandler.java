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

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Constants.Constants;
import XML.MyResultSet;
import XML.XMLRequest;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class requestHandler implements Runnable {

	private static Socket socket;
	private XMLRequest xmlRequest;
	private EntNetClient handleClient;
	public boolean setXML = false;
	private XMLRequest testRequest;
	private static ObjectInput in;
	public XMLRequest getTestRequest() {
		return testRequest;
	}

	public void setTestRequest(XMLRequest testRequest) {
		this.testRequest = testRequest;
	}

	// private
	public requestHandler(XMLRequest rq, EntNetClient enc) {
		xmlRequest = rq;
		handleClient = enc;
		if (socket == null) {
			try {
				socket = new Socket("localhost", 8189);
				System.out
						.println("Client Socket initialized, connect with server");
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
		synchronized (socket) {
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				// autoflush
				//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if (in == null) {
					InputStream o = socket.getInputStream();
					in = new ObjectInputStream(o);
				}

				out.println(xmlRequest.generateXMLRequest());
				if (xmlRequest.getRequestID().equals(Constants.QUIT_ID)) {
					return;
				}
				String resultString = new String();
				String onelineString = new String();
				while (!(onelineString = (String)in.readObject()).equals(Constants.INVALID)) {
				}
				while ((onelineString = (String)in.readObject())
						.equals(Constants.END_STRING) == false) {
						resultString += onelineString;
				}
				XMLRequest resultRequest = new XMLRequest(resultString);
				if (resultRequest.getRequestDetail().equals(
						Constants.RETURN_RESULTSET)) {
					while (!(onelineString = (String)in.readObject()).equals(Constants.RETURN_RESULTSET)) {
					}
					MyResultSet mrs = (MyResultSet) in.readObject();
					resultRequest.setMyResultSet(mrs);
				}
				testRequest = resultRequest;
				setXML = true;
                                try {
                    try {
                        handleClient.requestThreadCallBack(resultRequest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
			} catch (IOException e) {
				System.out.println("socket error");
				System.err.println(e.getLocalizedMessage());
				XMLRequest resultRequest = new XMLRequest(Constants.INVALID, Constants.INVALID, Constants.INVALID, 
						Constants.INVALID, Constants.INVALID, Constants.INVALID);
                                try {
                    try {
                        handleClient.requestThreadCallBack(resultRequest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
				e.printStackTrace();
			} // true means
			catch (ClassNotFoundException e) {
				XMLRequest resultRequest = new XMLRequest(Constants.INVALID, Constants.INVALID, Constants.INVALID, 
						Constants.INVALID, Constants.INVALID, Constants.INVALID);
                                try {
                    try {
                        handleClient.requestThreadCallBack(resultRequest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
				System.err.println("classNotFound");
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				XMLRequest resultRequest = new XMLRequest(Constants.INVALID, Constants.INVALID, Constants.INVALID, 
						Constants.INVALID, Constants.INVALID, Constants.INVALID);
                                try {
                    try {
                        handleClient.requestThreadCallBack(resultRequest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
				System.err.println("Parse Problem");
				e.printStackTrace();
			} catch (SAXException e) {
				System.err.println("Parse Problem");
				XMLRequest resultRequest = new XMLRequest(Constants.INVALID, Constants.INVALID, Constants.INVALID, 
						Constants.INVALID, Constants.INVALID, Constants.INVALID);
                                try {
                    try {
                        handleClient.requestThreadCallBack(resultRequest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
				e.printStackTrace();
			}
		}
	}

}
