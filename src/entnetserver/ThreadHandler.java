/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entnetserver;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.org.apache.bcel.internal.generic.NEW;

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
	private ObjectOutputStream oos;
	
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
		read = new Scanner(inStream);
		//write = new PrintWriter(outStream, true); // true means autoflush
		oos = new ObjectOutputStream(outStream);
	}

	/**
	 * @param result
	 *            threads callback the result
	 * @return the output of the SQLQuery, the String will forget afterwards.
	 * @throws IOException
	 */
	public void callBackResult(XMLRequest rq) {
		synchronized (oos) {
			synchronized (read) {
				try {
			oos.writeObject(new String("flush"));
			oos.writeObject(new String(Constants.INVALID));
			oos.writeObject(new String(rq.generateXMLRequest()));
			oos.writeObject(new String(Constants.END_STRING));
			//write.println(Constants.INVALID);
			//write.println(rq.generateXMLRequest());
			//write.println(Constants.END_STRING);
			if (rq.getRequestDetail() == Constants.RETURN_RESULTSET) {

					    oos.writeObject(new String(Constants.RETURN_RESULTSET));
						oos.writeObject(rq.getMyResultSet());
				} 
				}catch (IOException e) {
					System.err.println("err for writeobject");
					//oos.writeObject(new String(Constants.END_STRING));
					e.printStackTrace();
				}
				// write.println(rq.getMyResultSeStringt());
				// write.println(Constants.END_STRING);
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
			String xml = new String();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					incoming.getInputStream()));
			while ((xml = in.readLine()) == null) {
			}
			System.out.println("request formart : \n" + xml);
			XMLRequest request;
				request = new XMLRequest(xml);
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
				//write.println(Constants.END_STRING);
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				System.err.println("xml parse failed");
				//write.println(Constants.END_STRING);
				e.printStackTrace();
			} catch (SAXException e) {
				System.err.println("xml parse failed");
				//write.println(Constants.END_STRING);
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("ThreadHandle failed");
				//write.println(Constants.END_STRING);
				e.printStackTrace();
			}

		}
		// }
	}

}