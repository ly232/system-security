package entnetserver;

import junit.framework.TestCase;

public class CheckPasswordTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCheckPassword() {
		boolean result = true;
		String test1 = "adsfasdf";
		String test2 = "123123124";
		String test3 = "123sdfa";
		assertFalse(CheckPassword.checkPassword(test1));
		assertFalse(CheckPassword.checkPassword(test2));
		assertTrue(CheckPassword.checkPassword(test3));
	}

}
