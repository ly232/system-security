package Security;

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
			
			public  String decrpty(byte[] data, byte[] key){
				return null;
			}
			
			public  byte[] encrpty(String data, byte[] key){
				return null;
			}
			
			public  byte[] generateRandomKey(){
				return null;
			}
			
			public  byte[] generateKeyWithPwd(String pwd){
				return null;
			}
}
