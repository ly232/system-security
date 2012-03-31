package Security;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

public class MyKey implements Serializable{
	public SecretKey skey=null;
	

	public PBEParameterSpec pps=null;
	//public IvParameterSpec ips=null;
	
	public  PublicKey pubKey=null;
	public  PrivateKey privKey=null;
	

	public MyKey(){};

}
