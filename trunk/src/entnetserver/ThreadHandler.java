/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetserver;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Security.MyKey;
import Security.MyPKI;
import Security.SerilizeKey;
import Security.SharedKey;
import XML.*;

import Constants.Constants;
import JDBC.DataBase;

class ThreadedHandler implements Runnable {

	/**
	 * @param i
	 *            Socket with client
	 * @param db
	 *            the mysql database
	 */
	public ThreadedHandler(Socket i, DataBase db) {
		incoming = i;
		sysDB = db;
	}

	// ThreadHandler private variables:
	private Socket incoming;
	private final int MAX_LOGIN_TRAIL = 5;
	private String user_id = ""; // each thread serves a single user only. we
									// need to keep track of who the thread is
									// serving
	private String VERIFICATION_CODE = "cornell"; // used for user registration
	private Scanner read;
	//private PrintWriter write;
	private InputStream inStream;
	private OutputStream outStream;
	private int threadCount = 0;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	
	public OutputStream getOutStream() {
		return outStream;
	}

	public void setOutStream(OutputStream outStream) {
		this.outStream = outStream;
	}

	public DataBase getSysDB() {
		return sysDB;
	}

	public void setSysDB(DataBase sysDB) {
		this.sysDB = sysDB;
	}

	private DataBase sysDB;

	/**
	 * @param no
	 * @return void
	 * @throws IOException
	 *             from getInputStream();
	 */
	private void initializeServer() throws IOException {
		inStream = incoming.getInputStream();
		outStream = incoming.getOutputStream();
		//read = new Scanner(inStream);
		//write = new PrintWriter(outStream, true); // true means autoflush
		if (oos == null) {
			oos = new ObjectOutputStream(outStream);
		}
		if (ois == null) {
			ois = new ObjectInputStream(inStream);
		}

	}

	/**
	 * @param result
	 *            threads callback the result
	 * @return the output of the SQLQuery, the String will forget afterwards.
	 * @throws IOException
	 */
	public void callBackResult(XMLRequest rq) {
		synchronized (oos) {
				try {
					oos.writeObject(rq);
					if (rq.getRequestDetail() == Constants.RETURN_RESULTSET) {
						oos.writeObject(rq.getMyResultSet());
					} 
					if (rq.getRequestID() == Constants.SESSION_KEY_EST) {
						
					}
					}catch (IOException e) {
						System.err.println("err for writeobject");
						e.printStackTrace();
					}
		}	
		}

	

	public void run() {
		try {
			initializeServer();

		} catch (IOException e) {
			System.err.println("initial server fail");
			e.printStackTrace();
			return;
		} 
		while (true) {
			try {
			System.out.println("ready to serve");
				
			XMLRequest request = (XMLRequest)ois.readObject();
			//System.out.println(request.generateXMLRequest());
			
			 if (request.getRequestID().equals(Constants.SESSION_KEY_EST)) {
				 //XMLRequest.sessionKey = (MyKey)ois.readObject();
				 ServerSocket s = new ServerSocket(12345);
				 //oos.writeBoolean(true);
				 oos.writeObject(Constants.TRUE);
				 Socket sessionSocket = s.accept();
				 
				 PrivateKey privateKey = SerilizeKey.ReadPrivateKey(EntNetServer.pwd);
				 MyPKI mypki = MyPKI.getInstance();
				 Cipher desCipher = Cipher.getInstance(MyPKI.xform);
				    desCipher.init(Cipher.DECRYPT_MODE, privateKey);
				    // Create stream
				    BufferedInputStream bis = new BufferedInputStream(sessionSocket.getInputStream());
				    CipherInputStream cis = new CipherInputStream(bis, desCipher);
				    ObjectInputStream sessionObjectInput = new ObjectInputStream(cis);

				    // Read objects
				    MyKey sessionKey = new MyKey();
				    //String temp = (String)sessionObjectInput.readObject();
				    int length = sessionObjectInput.readInt();
				    byte[] keys = new byte[length];
				    sessionObjectInput.read(keys);
				    String algorithm = (String)sessionObjectInput.readObject();
				    byte[] salt = new byte[8];
				    sessionObjectInput.read(salt);
				    sessionKey.pps =  new PBEParameterSpec(salt, 8);
				    sessionKey.skey = new SecretKeySpec(keys,algorithm);
				    XMLRequest.sessionKey = sessionKey;
				    //sessionKey.skey = (SecretKey)sessionObjectInput.readObject();
				    sessionObjectInput.close();
				 
				 /*
				 String sessionString = request.getRequestDetail();
				 MyPKI mPki = MyPKI.getInstance();
				 PrivateKey pKey= SerilizeKey.ReadPrivateKey(null);
				 String seed = mPki.decrypt(sessionString.getBytes(), pKey);
				 SharedKey skKey = SharedKey.getInstance();
//				 XMLRequest.sessionKey = skKey.generateKeyWithPwd("1");
				 XMLRequest.sessionKey = skKey.generateKeyWithPwd("lin");
				 request.setRequestDetail(Constants.TRUE);
				 XMLRequest.sessionKey = skKey.generateKeyWithPwd(seed);
				 callBackResult(request);
				 */
				 continue;
			}
			
			request.decrypt(null);
			//System.out.println(request.generateXMLRequest());
			if (request.getRequestID().equals(Constants.QUIT_ID)) {
					return;
				}
				if (request.getRequestID().equals(Constants.LOGIN_REQUEST_ID)) {
					user_id = request.getUserID();
					loginServlet lServlet = new loginServlet(request, this);
					Thread t = new Thread(lServlet);
					t.start();
					threadCount++;
				} else {
					if (request.getActionID().equals(Constants.SELECT)) {
						ReadServlet rServelet = new ReadServlet(request, this);
						//Thread t = new Thread(rServelet);
						//t.start();
						rServelet.run();
						threadCount++;
					} else if (request.getActionID().equals(Constants.UPDATE)) {
						UpdateServlet uServlet = new UpdateServlet(request,
								this);
						Thread t = new Thread(uServlet);
						t.start();
						threadCount++;
					}
				}
			} catch (IOException e) {
				System.err.println("xml parse failed");
				System.err.println(e.getLocalizedMessage());
				//write.println(Constants.END_STRING);
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("ThreadHandle failed");
				System.err.println(e.getLocalizedMessage());
				//write.println(Constants.END_STRING);
				e.printStackTrace();
			}
		}
	}

}