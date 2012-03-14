package Security;

public interface SecurityObject {

	public String decrpty(byte[] data, byte[] key);

	public byte[] encrpty(String data, byte[] key);
	
}
