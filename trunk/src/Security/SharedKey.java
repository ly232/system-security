package Security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class SharedKey implements SecurityObject{
			//TODO: just illustrate the idea
			static SharedKey sKey;
			private SharedKey(){}
			static SharedKey getInstance(){
				if (sKey == null) {
					sKey = new SharedKey();
				}
				return sKey;
			}
			
			public  String decrypt(byte[] data, MyKey mk){
				byte[] resultArray;
				String result=null;
				// Create PBE Cipher
	            Cipher pbeCipher=null;
				try {
					pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
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
					resultArray= pbeCipher.doFinal(data);
					result= new String(resultArray);
					
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}
	            return result;
			}
			

			
			public  byte[] encrypt(String data, MyKey mk){
				// Create PBE Cipher
	            Cipher pbeCipher=null;
	            byte[] ciphertext = null;
	            
				try {
					pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
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

	            byte[] cleartext = data.getBytes();

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
	            
	            MyKey mk = new MyKey();
	            mk.skey = pbeKey;
	            mk.pps= generatePBEParaSpec();
	            
				return mk;
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
			
			
			public  byte[] generateRandomKey(){
				return null;
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
