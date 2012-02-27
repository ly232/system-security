package entnetclient;

import entnetserver.EntNetServer;
import Constants.Constants;
import XML.XMLRequest;
import junit.framework.TestCase;

public class requestHandlerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRequestHandler() {
		XMLRequest xmlRequest = new XMLRequest(Constants.READ_REGION_ID, "tao",
															Constants.REGION0, null, "select * from user;", Constants.SELECT);
		//EntNetClient testEntNetClient = EntNetClient.getInstance();
		//requestHandler hrHandler = new requestHandler(xmlRequest);
		//hrHandler.run();
		//("Not yet implemented");
	}

	public void testRun() {
		//fail("Not yet implemented");
	}

}
