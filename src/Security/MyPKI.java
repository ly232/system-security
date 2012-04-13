package Security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.omg.CORBA.portable.InputStream;

import JDBC.DataBase;



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
		
		kpg.initialize(1024, sr);
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
	
	
	//Lin-4/11/12
	//generates a random pair of public/private key pairs, then save it to a file.
	//this method is called each time a server starts. so a new pair of pub/priv keys are generated for each server run
	public MyKey InitAsymmKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IOException{
		MyKey mk = new MyKey();
		kpg = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
		kpg.initialize(1024, random);
		kp = kpg.generateKeyPair();
		PrivateKey privKey = kp.getPrivate();
		PublicKey pubKey = kp.getPublic();
		
		//KeyFactory fact = KeyFactory.getInstance("RSA");
		//RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),RSAPublicKeySpec.class);
		//RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),RSAPrivateKeySpec.class);
		//saveToFile("public.key", pub.getModulus(),pub.getPublicExponent());
		mk.pubKey = pubKey;
		mk.privKey = privKey;
		
		return mk;
		
		//saveToFile("private.key", priv.getModulus(),priv.getPrivateExponent());
		//for private key, store to database.
		//byte[] privModByteArr = priv.getModulus().toByteArray();
		//byte[] privExpByteArr = priv.getPrivateExponent().toByteArray();
		//p.s. to get back to big int, use constructor BigInteger(byte[] val).
		//first, delete existing entry in server private key table.
		//String deleteQuery = "delete from serverprivkey";
		//System.out.println("deleteQuery: "+deleteQuery);
		//db.DoUpdateQuery(deleteQuery);
		//then, encrypte private key using db_pwd...i.e. password based encryption:
		
		
		/*
		String insertQuery = "insert into serverprivkey (privMod, privExp, primKey) values " +
				"(aes_encrypt('"+privModByteArr+"','"+db_pwd+"'), " +
						"aes_encrypt('"+privExpByteArr+"','"+db_pwd+"'), 1)";
		System.out.println("insertQuery: "+insertQuery);
		db.DoUpdateQuery(insertQuery);
		*/
		
		
	}
	public PublicKey readPublicKeyFromFile(String keyFileName) throws IOException {
		FileInputStream in = new FileInputStream(keyFileName);
		    //ServerConnection.class.getResourceAsStream(keyFileName);
		  ObjectInputStream oin =
		    new ObjectInputStream(new BufferedInputStream(in));
		  try {
		    BigInteger m = (BigInteger) oin.readObject();
		    BigInteger e = (BigInteger) oin.readObject();
		    RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
		    KeyFactory fact = KeyFactory.getInstance("RSA");
		    PublicKey pubKey = fact.generatePublic(keySpec);
		    return pubKey;
		  } catch (Exception e) {
		    throw new RuntimeException("Spurious serialisation error", e);
		  } finally {
		    oin.close();
		  }
	}
	//Lin-4/11/12
	public byte[] rsaEncrypt(PublicKey pubKey, byte[] src) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		  //PublicKey pubKey = readKeyFromFile("/public.key");
		  Cipher cipher = Cipher.getInstance("RSA");
		  cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		  byte[] cipherData = cipher.doFinal(src);
		  return cipherData;
	}
	public byte[] rsaDecrypt(PrivateKey privKey, byte[] src) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		  Cipher cipher = Cipher.getInstance("RSA");
		  cipher.init(Cipher.DECRYPT_MODE, privKey);
		  byte[] decipherData = cipher.doFinal(src);
		  return decipherData;
	}
	//Lin-4/11/12
	public void saveToFile(String fileName,
			  BigInteger mod, BigInteger exp) throws IOException {
			  ObjectOutputStream oout = new ObjectOutputStream(
			    new BufferedOutputStream(new FileOutputStream(fileName)));
			  try {
			    oout.writeObject(mod);
			    oout.writeObject(exp);
			  } catch (Exception e) {
			    throw new IOException("Unexpected error", e);
			  } finally {
			    oout.close();
			  }
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
