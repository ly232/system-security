package Security;

public class PublicKey implements SecurityObject{
	
	static PublicKey pKey;
	private PublicKey(){}
	static PublicKey getInstance(){
		if (pKey == null) {
			pKey = new PublicKey();
		}
		return pKey;
	}
	
	public  String decrypt(byte[] data, byte[] key){
		return null;
	}
	
	public  byte[] encrypt(String data, byte[] key){
		return null;
	}
}