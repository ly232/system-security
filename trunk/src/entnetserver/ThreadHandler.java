/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetserver;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//import com.sun.org.apache.bcel.internal.generic.NEW;

import Security.MyKey;
import Security.MyPKI;
import Security.SerilizeKey;
import Security.SharedKey;
import XML.*;

import Constants.Constants;
import JDBC.DataBase;
import Security.*;

class ThreadedHandler implements Runnable {
   
	private SharedKey symmKeyCryptoAPI = null;
    public static String db_pwd = null;
    private Socket incoming = null;
	private final int MAX_LOGIN_TRAIL = 5;
	public String user_id = ""; // keep track of the assocated user with respect to this thread
	public int roleID;
	private Scanner read = null;
	private InputStream inStream = null;
	private OutputStream outStream = null;
	private int threadCount = 0;
	private  ObjectOutputStream oos;
	private  ObjectInputStream ois;
	private DataBase sysDB;
	
	public static PrivateKey k_server;
	public static PublicKey K_server;
	public  String k_session;

	
	
	
	/**
	 * @param i
	 *            Socket with client
	 * @param db
	 *            the mysql database
	 */
	public ThreadedHandler(Socket i, DataBase db, String dbpwd, PrivateKey k_server, PublicKey K_server) {
		incoming = i;
		sysDB = db;
        db_pwd = dbpwd;
        symmKeyCryptoAPI = SharedKey.getInstance();
        this.k_server = k_server;
        this.K_server = K_server;
        
	}
    public String getDBpwd(){
            return this.db_pwd;
    }
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

	

	/**
	 * @param no
	 * @return void
	 * @throws IOException
	 *             from getInputStream();
	 */
	private void initializeServer() throws IOException {
		inStream = incoming.getInputStream();
		outStream = incoming.getOutputStream();
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
			initializeServer(); //sets up input/out streams
			System.out.println("ThreadHandler: ready to serve client "+this.user_id);

		} catch (IOException e) {
			System.err.println("initial server fail");
			e.printStackTrace();
			return;
		} 
		while (true) {
			try {
			 	
			 XMLRequest request = (XMLRequest)ois.readObject();
			 
			 if (request.getRequestID().equals(Constants.REQ_SERVER_PUBKEY)) {
				 //ServerSocket s = new ServerSocket(12346);
				 KeyFactory fact = KeyFactory.getInstance("RSA");
				 RSAPublicKeySpec pub = fact.getKeySpec(K_server,RSAPublicKeySpec.class);
				 oos.writeObject(pub.getModulus());
				 oos.writeObject(pub.getPublicExponent());
				 continue;
			 }
			 
			 if (request.getRequestID().equals(Constants.SESSION_KEY_EST)) {
				 //decrypt cipher_sessionKey with private key:
				 MyPKI mypki = MyPKI.getInstance();
				 byte[] k_session_byteArr = mypki.rsaDecrypt(k_server, request.cipher_sessionKey);
				 k_session = new String(k_session_byteArr);
				 System.out.println("server: k_session = "+k_session);
				 
				 continue;
			 }

			 	//request.decrypt(); //commented out by Lin-4/12/12
    
			if (request.getRequestID().equals(Constants.QUIT_ID)) {
				return;
			}
                              
				if (request.getRequestID().equals(Constants.LOGIN_REQUEST_ID)) {           
					user_id = request.getUserID();
					loginServlet lServlet = new loginServlet(request, this);
					Thread t = new Thread(lServlet);
					t.start();
					threadCount++;
				} 
				else {
					if (request.getActionID().equals(Constants.SELECT)) {
						ReadServlet rServelet = new ReadServlet(request, this);
						//Thread t = new Thread(rServelet);
						//t.start();
						rServelet.run();
						threadCount++;
					} 
					else if (request.getActionID().equals(Constants.UPDATE)) {
						UpdateServlet uServlet = new UpdateServlet(request,
								this);
						Thread t = new Thread(uServlet);
						t.start();
						threadCount++;
					}
					else{
						System.err.println("ThreadHandler: unrecognized xml request from client");
					}
				}
			} catch (IOException e) {
				System.err.println("read  failed");
				System.err.println(e.getLocalizedMessage());
				//write.println(Constants.END_STRING);
				e.printStackTrace();
				return;
			} catch (Exception e) {
				System.err.println("ThreadHandle failed");
				System.err.println(e.getLocalizedMessage());
				//write.println(Constants.END_STRING);
				e.printStackTrace();
			}
		}
	}

}