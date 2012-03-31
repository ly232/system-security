package Security;

import java.security.PrivateKey;
import java.security.PublicKey;

import junit.framework.TestCase;

public class SerilizeKeyTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testWritePublicKey() {
		MyPKI mp = MyPKI.getInstance();
		MyKey myKey = mp.generateKeyPair();
		PublicKey pKey = myKey.pubKey;
		PrivateKey prKey = myKey.privKey;
		SerilizeKey.WritePublicKey(pKey);
		PublicKey testPublicKey = SerilizeKey.ReadPublicKey();
		assertEquals(pKey, testPublicKey);
		SerilizeKey.WritePrivateKey(prKey,"123");
		PrivateKey testPrKey = SerilizeKey.ReadPrivateKey("123");
		assertEquals(prKey, testPrKey);
		//fail("Not yet implemented");
	}

	public void testReadPublicKey() {
		fail("Not yet implemented");
	}

	public void testWritePrivateKey() {
		fail("Not yet implemented");
	}

	public void testReadPrivateKey() {
		fail("Not yet implemented");
	}

}
