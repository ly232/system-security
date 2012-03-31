package Security;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;



import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


public class MyKey implements Serializable{
	public SecretKey skey=null;
	

	public PBEParameterSpec pps=null;
	//public IvParameterSpec ips=null;
	
	public  PublicKey pubKey=null;
	public  PrivateKey privKey=null;
	

	public MyKey(){};
        
        public byte[] getSecretKeyByteArr(){
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
                KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
                keyGen.init(256);
                Key wrapKey = (Key) keyGen.generateKey();
                cipher.init(Cipher.ENCRYPT_MODE, wrapKey);
                byte[] wrappedKey = cipher.doFinal(skey.getEncoded());
                
                return wrappedKey;


            } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(MyKey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(MyKey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(MyKey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(MyKey.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(MyKey.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(MyKey.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
            
        }

}
