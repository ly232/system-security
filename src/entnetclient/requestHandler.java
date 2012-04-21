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
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Constants.Constants;
import Security.MyPKI;
import Security.SerilizeKey;
import XML.MyResultSet;
import XML.XMLRequest;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
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

	public requestHandler(XMLRequest rq, EntNetClient enc) {
		xmlRequest = rq;
		handleClient = enc;
		if (socket == null) {
			try {
				socket = new Socket("localhost", 8189);
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

		synchronized (socket) {
			try {
				if (out == null) {
					 out = new ObjectOutputStream(socket.getOutputStream());
				}
				//-----------------
				//send request:
				//-----------------
				if (xmlRequest.getRequestID().equals(Constants.REQ_SERVER_PUBKEY)) {	
					
					System.out.println("request handler: requesting server public key..");
					
					out.writeObject(xmlRequest);
					if (in == null) {
						InputStream o = socket.getInputStream();
						in = new ObjectInputStream(o);
					}
					BigInteger K_server_mod = (BigInteger) in.readObject();
					BigInteger K_server_exp = (BigInteger) in.readObject();
					//reconstruct server public key:
					RSAPublicKeySpec keySpec = 
						new RSAPublicKeySpec(K_server_mod, K_server_exp);
					KeyFactory fact = KeyFactory.getInstance("RSA");
					handleClient.K_server = fact.generatePublic(keySpec);
					
					System.out.println("establishing session key with server...");
					
					handleClient.establishSessionKey();
					return;
				}
				else if (xmlRequest.getRequestID().equals(Constants.SESSION_KEY_EST)){
					out.writeObject(xmlRequest);
					return;
				}
				else if (xmlRequest.getRequestID().equals(Constants.QUIT_ID)) {
					out.writeObject(xmlRequest); //notify server to close the thread
					return;
				}
				else{ //read region requests...
					out.writeObject(xmlRequest);
					//do not return!!! wait for server's response
				}
				//-------------------
				//receive request:
				//-------------------

				
				
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
			} catch (IOException err) {
				System.out.println("socket error");
				System.err.println(err.getLocalizedMessage());
				XMLRequest resultRequest = new XMLRequest(Constants.INVALID, Constants.INVALID, Constants.INVALID, 
						Constants.INVALID, Constants.INVALID, Constants.INVALID);
                                try {
                    try {
                        handleClient.requestThreadCallBack(resultRequest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
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
                                } catch (SQLException ex) {
                                    Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
				err.printStackTrace();
			} // true means
			catch (ClassNotFoundException e1) {
				XMLRequest resultRequest = new XMLRequest(Constants.INVALID, Constants.INVALID, Constants.INVALID, 
						Constants.INVALID, Constants.INVALID, Constants.INVALID);
                                try {
                    try {
                        handleClient.requestThreadCallBack(resultRequest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
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
                                } catch (SQLException ex) {
                                    Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
				System.err.println("classNotFound");
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
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

}

