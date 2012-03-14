package Security;

public interface SecurityObject {

	public String decrypt(byte[] data, byte[] key);

	public byte[] encrypt(String data, byte[] key);
	
}
