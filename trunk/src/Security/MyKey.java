package Security;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

public class MyKey {
	public SecretKey skey;
	public PBEParameterSpec pps;
	
	public  PublicKey pubKey;
	public  PrivateKey privKey;
	
	public MyKey(){};

}
