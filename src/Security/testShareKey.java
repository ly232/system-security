package Security;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.crypto.SecretKey;

public class testShareKey {

	/**
	 * @param args
	 */
/*	
	public static final char[] readPassword(InputStream in)
            throws IOException {

        if (in == System.in && System.console() != null) {
            // readPassword returns "" if you just print ENTER,
            return System.console().readPassword();
        }
        else return null;
}
*/
	

	public static void main(String[] args) {
		SharedKey sk=SharedKey.getInstance();
		
		System.out.println("please enter password");
		DataInputStream ins = new DataInputStream(System.in);
		
	    String PwdStr="";
		InputStreamReader stdin = new InputStreamReader(System.in);
		BufferedReader bfrdr = new BufferedReader(stdin);
		

		try {
			PwdStr = bfrdr.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    MyKey MysecKey=sk.generateKeyWithPwd(PwdStr);
	    
	    System.out.println("Generated key is:"+ MysecKey);
		
	    
	    System.out.println("Generated secretSpec is:"+ MysecKey.pps);
	    
	    System.out.println("please enter plaintext:");
	    
	    String plainText="";
		try {
			plainText = bfrdr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Before encrypt the plain text is:"+ plainText);
	    
	    byte [] encrptText=sk.encrypt(plainText, MysecKey);
	    System.out.println("After encrypt the plain text is:"+ encrptText.toString());
	    
	    String decrptText=sk.decrypt(encrptText, MysecKey);
	    System.out.println("After decrypt the plain text is:"+ decrptText);
    
	    ///////////////////////////////////////////////////////////////////////
	    System.out.println("==============random key testing============");
	    
	    MyKey randomKey=sk.generateRandomKey();
	    String str = "lin";
	    sk.encrypt(str, randomKey);
	    String result = sk.decrypt(sk.encrypt(str, randomKey), randomKey);
	    System.out.println(result);
	    
	    System.out.println("Generated key is:"+ randomKey);
	    
	    
	    System.out.println("please enter plaintext:");
	    
	    String plainText3="";
		try {
			plainText3 = bfrdr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Before encrypt the plain text is:"+ plainText3);
	    byte [] encrptText3=sk.encrypt(plainText3, randomKey);
	    System.out.println("After encrypt the plain text is:"+ encrptText3.toString());
	    
	    String decrptText3=sk.decrypt(encrptText3, randomKey);
	    System.out.println("After decrypt the plain text is:"+ decrptText3);
	    
	    
	    
	    //////////////////////////////////////////////////////////////////////
	    MyPKI myPkiTest= MyPKI.getInstance();
	    MyKey myKeyTest = myPkiTest.generateKeyPair();
	    
	    System.out.println("==============Key pair testing============");
	    System.out.println("The public key is:"+ myKeyTest.pubKey);
	    System.out.println("The public key is:"+ myKeyTest.privKey);
	    
	    System.out.println("please enter plaintext:");
	    
	    String plainText2="";
		try {
			plainText2 = bfrdr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Before encrypt the plain text is:"+ plainText2);
	    
	    byte [] encrptText2=myPkiTest.encrypt(plainText2, myKeyTest.pubKey);
	    System.out.println("After encrypt the plain text is:"+ encrptText2.toString());
	    
	    String decrptText2=myPkiTest.decrypt(encrptText2, myKeyTest.privKey);
	    System.out.println("After decrypt the plain text is:"+ decrptText2);
	    
	    
	 
	    
	    ///////////////////////////////////////////////////////////////////////
	    
	    //MyPKI myPkiTest= MyPKI.getInstance();
	    //MyKey myKeyTest = myPkiTest.generateKeyPair();
	    
	    System.out.println("==============Key pair testing============");
	    System.out.println("The public key is:"+ myKeyTest.pubKey);
	    System.out.println("The public key is:"+ myKeyTest.privKey);
	    
	    System.out.println("please enter plaintext:");
	    
	    //String plainText2="";
		try {
			plainText2 = bfrdr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Before encrypt the plain text is:"+ plainText2);
	    
	    encrptText2=myPkiTest.encrypt(plainText2, myKeyTest.pubKey);
	    System.out.println("After encrypt the plain text is:"+ encrptText2.toString());
	    
	    decrptText2=myPkiTest.decrypt(encrptText2, myKeyTest.privKey);
	    System.out.println("After decryot the plain text is:"+ decrptText2);
	    
	}

}
