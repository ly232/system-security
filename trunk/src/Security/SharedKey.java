package Security;

import java.io.UnsupportedEncodingException;
import java.rmi.server.UID;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Encoder;

public class SharedKey implements SecurityObject{
			//TODO: just illustrate the idea
			static SharedKey sKey;
			MyKey mk = new MyKey();
			static final String xform = "PBEWithMD5AndDES";
			private SharedKey(){}
			public static SharedKey getInstance(){
				if (sKey == null) {
					sKey = new SharedKey();
				}
				return sKey;
			}
			
			public  String decrypt(byte[] data, MyKey mk){
				if (data == null) {
					return null;
				}
				byte[] resultArray;
				String result = "";
				
				// Create PBE Cipher
	            Cipher pbeCipher=null;
				try {
					pbeCipher = Cipher.getInstance(xform);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}

	            // Initialize PBE Cipher with key and parameters
	            try {
					pbeCipher.init(Cipher.DECRYPT_MODE, mk.skey, mk.pps);
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (InvalidAlgorithmParameterException e1) {
					e1.printStackTrace();
				}
				
	            try {
                        //int remainder = data.length % 8;
                        
                        
					resultArray= pbeCipher.doFinal(data);
					/*
					for (int i = 0; i < resultArray.length; i++) {
						result += Integer.toHexString((0x000000ff & resultArray[i]) | 0xffffff00).substring(6);
					}
					*/
					result= new String(resultArray);
					
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}
	            return result;
			}
			

			
			public  byte[] encrypt(String data, MyKey mk){
				if (data == null) {
					return null;
				}
				// Create PBE Cipher
	            Cipher pbeCipher=null;
	            byte[] ciphertext = null;
	            
				try {
						pbeCipher = Cipher.getInstance(xform);	
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}

	            // Initialize PBE Cipher with key and parameters
	            try {
					pbeCipher.init(Cipher.ENCRYPT_MODE, mk.skey, mk.pps);
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (InvalidAlgorithmParameterException e1) {
					e1.printStackTrace();
				}

	            byte[] cleartext;
	            if (data == null) {
	            	  cleartext = null;
				}else {
					 cleartext = data.getBytes();
				}
	            

	            // Encrypt the cleartext
	            try {
					//byte[] ciphertext = null;
					ciphertext=pbeCipher.doFinal(cleartext);
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}
				
				return ciphertext;
			}
	
			
		
			
			public  MyKey generateKeyWithPwd(String pwd){
				 
		         SecretKeyFactory keyFac=null;
		         SecretKey pbeKey=null;
		         
		        // byte[] passwordByte=pwd.getBytes();
		         char[] passwordChar =pwd.toCharArray();

		         
	            //convert the password into a SecretKey object, using a PBE key factory.
		        PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordChar);
	            try {
					
						keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
					
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
	            try {
					pbeKey = keyFac.generateSecret(pbeKeySpec);
				} catch (InvalidKeySpecException e) {
					e.printStackTrace();
				}
	            
	            
	            mk.skey = pbeKey;

	            byte[] salt = pwd.getBytes();
	            byte[] s = new byte[8];
	            for (int i = 0; i < s.length; i++) {
					s[i] = (byte) i;
				}
	            //int count = salt.length;
	            mk.pps = new PBEParameterSpec(s, 8);
	            

				return mk;
			}
			
			
			
			public SecretKey generateWithInt(int gene){
				try {
					KeyGenerator generator;
					generator = KeyGenerator.getInstance("DES");
					generator.init(gene);
					SecretKey key = generator.generateKey();
					return key;
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			}
			
			/*
			private String encrypt(String message,SecretKey key) {
		// Get a cipher object.
					Cipher cipher;
					try {
						cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
						cipher.init(Cipher.ENCRYPT_MODE, key);
						 
						// Gets the raw bytes to encrypt, UTF8 is needed for
						// having a standard character set
						byte[] stringBytes = message.getBytes("UTF8");
					 
						// encrypt using the cypher
						byte[] raw = cipher.doFinal(stringBytes);
					 
						// converts to base64 for easier display.
						BASE64Encoder encoder = new BASE64Encoder();
						String base64 = encoder.encode(raw);
					 
						return base64;
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidKeyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalBlockSizeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return null;
	}

			
			
			public PBEParameterSpec generatePBEParaSpec(){
			
			 SecureRandom r = new SecureRandom();
	         byte[] salt = new byte[8];
	         //parameter is the array to be filled in with random bytes
	         r.nextBytes(salt);
	         
	         int count = 8;
	         
	         PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
	         
	         return pbeParamSpec;
		}
			
		
			public MyKey generateRandomKey(){
				UID uid= new UID();
				return this.generateKeyWithPwd(uid.toString());
				
			}
			*/
			
			
	/*		
	
	public MyKey generateRandomKey(){
				
				KeyGenerator generator=null;
				try {
					generator = KeyGenerator.getInstance("DES");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				generator.init(new SecureRandom());
				SecretKey randomKey = generator.generateKey();
				
				mk.skey = randomKey;
				mk.ips=generateIvParaSpec();
	            
				return mk;
				//Random pswNum= new Random();
				//return this.generateKeyWithPwd(pswNum.toString());
				
			}

			public IvParameterSpec generateIvParaSpec(){
				
				SecureRandom iv = new SecureRandom();
		         byte[] salt = new byte[8];
		         //parameter is the array to be filled in with random bytes
		        iv.nextBytes(salt);
		        
				IvParameterSpec ips = new IvParameterSpec(salt);
		        return ips;
			}	
	
	*/	
			
			
			
			
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
