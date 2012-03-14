package Security;

public class SharedKey {
			//TODO: just illustrate the idea
			static SharedKey sKey;
			private SharedKey(){}
			static SharedKey getInstance(){
				if (sKey == null) {
					sKey = new SharedKey();
				}
				return sKey;
			}
			
			public static String decrpty(byte[] data, byte[] key){
				return null;
			}
			
			public static byte[] encrpty(String data, byte[] key){
				return null;
			}
			
			public static byte[] generateRandomKey(){
				return null;
			}
			
			public static byte[] generateKeyWithPwd(String pwd){
				return null;
			}
}
