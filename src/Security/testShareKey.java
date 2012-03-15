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
		//char[] readPwd=System.console().readLine();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//=System.console().readLine();
	    System.out.println("Before encrypt the plain text is:"+ plainText);
	    
	    byte [] encrptText=sk.encrypt(plainText, MysecKey);
	    System.out.println("After encrypt the plain text is:"+ encrptText.toString());
	    
	    String decrptText=sk.decrypt(encrptText, MysecKey);
	    System.out.println("After decryot the plain text is:"+ decrptText);
        
	}

}
