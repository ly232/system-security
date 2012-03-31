package Security;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;



//import sun.crypto.provider.*;


public class MyPKI implements SecurityObject{
	
	static MyPKI pKey;
	
	KeyPairGenerator kpg;
	KeyPair kp;
	PublicKey pubKey;
	PrivateKey privKey;
	Signature sig;
	
    public static String xform = "RSA/NONE/PKCS1Padding";
        
	private MyPKI(){
            Security.addProvider(new BouncyCastleProvider());
        
        }
	
	public static MyPKI getInstance(){
		if (pKey == null) {
			pKey = new MyPKI();
		}
		return pKey;
	}
	
	
	
	
	public MyKey generateKeyPair(){
		SecureRandom sr= new SecureRandom();
		byte[] salt= new byte[8];
		sr.nextBytes(salt);
		
		try {
			kpg=KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		kpg.initialize(512, sr);
		kp=kpg.generateKeyPair();
		
		pubKey=kp.getPublic();
		privKey=kp.getPrivate();
		
		System.out.println("The public key is:"+ pubKey.toString());
		System.out.println("The private key is:"+privKey.toString());
		
		
		MyKey mk = new MyKey();
        mk.pubKey = pubKey;
        mk.privKey = privKey;
		
        return mk;
		
	}
	public  String decrypt(byte[] data, PrivateKey privKey){
		byte[] cryptedText= data;
		String plainText=null;
		Cipher cipher=null;
		
		try {
			cipher= Cipher.getInstance(xform);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	    try {
			cipher.init(Cipher.DECRYPT_MODE, privKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	    try {
			//plainText=cipher.doFinal(cryptedText).toString();
                        byte[] test = cipher.doFinal(cryptedText);
                        plainText = new String(test);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return plainText;
	}
	
	public  byte[] encrypt(String data, PublicKey pubKey){
		byte[] plainText= data.getBytes();
		byte[] cryptedText=null;
		
		Cipher cipher=null;
		try {
			cipher = Cipher.getInstance(xform);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	    try {
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	    try {
			cryptedText=cipher.doFinal(plainText);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	    return cryptedText;
		
		
	}

	@Override
	public String decrypt(byte[] data, byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] encrypt(String data, byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}
}
