package Security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.sun.corba.se.spi.ior.Writeable;

public class SerilizeKey {
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
			
			public static void WritePrivateKey(PrivateKey key, String pwd){
				try {
                                    
					FileOutputStream fos = new FileOutputStream("privateKey.data");
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
			
			public static PrivateKey ReadPrivateKey(){
				try {
					FileInputStream fis;
					fis = new FileInputStream("privateKey.data");
					ObjectInputStream ois = new ObjectInputStream(fis);
					PrivateKey pKey = (PrivateKey)ois.readObject();
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
}
