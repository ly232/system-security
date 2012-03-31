package entnetclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Constants.Constants;
import Security.MyPKI;
import Security.SerilizeKey;
import XML.MyResultSet;
import XML.XMLRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class requestHandler implements Runnable {

	private static Socket socket;
	private XMLRequest xmlRequest;
	private EntNetClient handleClient;
	public boolean setXML = false;
	private XMLRequest testRequest;
	private static ObjectInput in;
	private static ObjectOutputStream out;
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
				//PrintWriter out = new PrintWriter(socket.getOutputStream(),
				//		true);
				if (out == null) {
					 out = new ObjectOutputStream(socket.getOutputStream());
				}
				// autoflush
				//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if (in == null) {
					InputStream o = socket.getInputStream();
					in = new ObjectInputStream(o);
				}
				//exchange the session key with the server
				if (xmlRequest.getRequestID().equals(Constants.SESSION_KEY_EST)) {
					xmlRequest.encrypt();
					out.writeObject(xmlRequest);
					String tempString = (String)in.readObject();
					if (tempString.equals(Constants.TRUE)) {
						MyPKI mypki = MyPKI.getInstance();
			            PublicKey pubkey = SerilizeKey.ReadPublicKey();
			             //send the session key to the server
							Socket sessionSocket = new Socket("localhost", 12345);
							// Create Cipher
						    Cipher desCipher = Cipher.getInstance(MyPKI.xform);
						    desCipher.init(Cipher.ENCRYPT_MODE, pubkey);

						    // Create stream
						    BufferedOutputStream bos = new BufferedOutputStream(sessionSocket.getOutputStream());
						    CipherOutputStream cos = new CipherOutputStream(bos, desCipher);
						    ObjectOutputStream sessionOos = new ObjectOutputStream(cos);

						    // Write objects
						   sessionOos.writeObject(Constants.SESSION_KEY_EST);
						    //sessionOos.writeObject(XMLRequest.sessionKey.skey);
						    sessionOos.flush();
						    sessionOos.close();
						    sessionSocket.close();
					}else {
						System.out.print("error");
					}
					    return;
				}
				//out.println(xmlRequest.generateXMLRequest());
				xmlRequest.encrypt();
				out.writeObject(xmlRequest);
				
				if (xmlRequest.getRequestID() == Constants.SESSION_KEY_EST) {
					//out.writeObject()
					//out.writeObject(XMLRequest.sessionKey);
				}
				
				if (xmlRequest.getRequestID().equals(Constants.QUIT_ID)) {
					return;
				}
				XMLRequest resultRequest = (XMLRequest)in.readObject();
				if (resultRequest.getRequestDetail().equals(
						Constants.RETURN_RESULTSET)) {
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
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

}

/*
String resultString = new String();
String onelineString = new String();
byte b[] = new byte[1000];
//ObjectInputStream ois = new ObjectInputStream(bais);
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.write(Constants.INVALID.getBytes());
b = baos.toByteArray();
ByteArrayInputStream bais = new ByteArrayInputStream(b);
byte test[] = new byte[b.length];
bais.read(test);
ByteArrayInputStream transfer = new ByteArrayInputStream(test);
ObjectInputStream ois = new ObjectInputStream(transfer);
String string = (String)ois.readObject();
*/
