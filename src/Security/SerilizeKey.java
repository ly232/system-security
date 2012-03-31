package Security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

import sun.print.PeekGraphics;



import com.sun.corba.se.spi.ior.Writeable;

public class SerilizeKey {
    
    public static void main(String[] args){
        MyPKI mypki = MyPKI.getInstance();
        MyKey mk = mypki.generateKeyPair();
        WritePublicKey(mk.pubKey);
        WritePrivateKey(mk.privKey, "");//TODO: add password as second argument
        
    }
    
			public static void WritePublicKey(PublicKey key){
				try {
					FileOutputStream fos = new FileOutputStream("publicKey.data");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(key);
					oos.flush();
					oos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			public static PublicKey ReadPublicKey(){
				try {
					FileInputStream fis;
					fis = new FileInputStream("publicKey.data");
					ObjectInputStream ois = new ObjectInputStream(fis);
					PublicKey pKey = (PublicKey)ois.readObject();
					ois.close();
					return pKey;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			public static void WritePrivateKey(PrivateKey keys, String pwd){
				try {
					// Create Key
				    byte key[] = pwd.getBytes();
				    DESKeySpec desKeySpec = new DESKeySpec(key);
				    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

				    // Create Cipher
				    Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				    desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

				    // Create stream
				    FileOutputStream fos = new FileOutputStream("privateKey.data");
				    BufferedOutputStream bos = new BufferedOutputStream(fos);
				    CipherOutputStream cos = new CipherOutputStream(bos, desCipher);
				    ObjectOutputStream oos = new ObjectOutputStream(cos);

				    // Write objects
				    oos.writeObject(keys);
				    oos.flush();
				    oos.close();
				    /*
					FileOutputStream fos = new FileOutputStream("privateKey.data");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(key);
					oos.flush();
					oos.close();
					
					FileOutputStream fos = new FileOutputStream("privateKey.data");
					DataOutputStream dos = new DataOutputStream(fos);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(key);
					oos.flush();
					oos.close();
					byte[] temp = baos.toByteArray();
					String storeString = new String(temp);
					SharedKey sk = SharedKey.getInstance();
					MyKey DBkey = sk.generateKeyWithPwd(pwd);
					System.out.println(DBkey);
					byte[] newtemp =  sk.encrypt(storeString, DBkey);
					
					dos.writeInt(newtemp.length);
					System.out.println(newtemp.length);
					System.out.println(newtemp);
					//dos.write(temp,0,temp.length);
					for (int i = 0; i < newtemp.length; i++) {
							dos.writeByte(newtemp[i]);
					}
					dos.flush();
					dos.close();
					fos.close();*/
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public static PrivateKey ReadPrivateKey(String pwd){
				try {
					// Create Key
				    byte key[] = pwd.getBytes();
				    DESKeySpec desKeySpec = new DESKeySpec(key);
				    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

				    // Create Cipher
				    Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				    desCipher.init(Cipher.DECRYPT_MODE, secretKey);

				    // Create stream
				    FileInputStream fis = new FileInputStream("privateKey.data");
				    BufferedInputStream bis = new BufferedInputStream(fis);
				    CipherInputStream cis = new CipherInputStream(bis, desCipher);
				    ObjectInputStream ois = new ObjectInputStream(cis);

				    // Read objects
				    PrivateKey pKey = (PrivateKey)ois.readObject();
				    ois.close();
				    return pKey;
					/*
					FileInputStream fis;
					fis = new FileInputStream("privateKey.data");
					ObjectInputStream ois = new ObjectInputStream(fis);
					PrivateKey pKey = (PrivateKey)ois.readObject();
					ois.close();
					return pKey;
					
					/*
					SharedKey sk = SharedKey.getInstance();
					MyKey DBkey = sk.generateKeyWithPwd(pwd);
					System.out.println(DBkey);
					FileInputStream fis = new FileInputStream("privateKey.data");
					DataInputStream dis = new DataInputStream(fis);
					int length = dis.readInt();
					System.out.println(length);
					byte[] temp = new byte[length];
					for (int i = 0; i < temp.length; i++) {
						temp[i] = dis.readByte();
					}
					System.out.println(temp);
					//dis.readByte();
					//fis.read(temp);
					//dis.readFully(temp);
					String getback = sk.decrypt(temp, DBkey);
					ByteArrayInputStream bais = new ByteArrayInputStream(getback.getBytes());
					ObjectInputStream ois = new ObjectInputStream(bais);
					PrivateKey pKey = (PrivateKey)ois.readObject();
					ois.close();
					return pKey;*/
					
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			
}
