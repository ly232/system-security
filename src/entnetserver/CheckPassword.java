package entnetserver;

public class CheckPassword {
			static public boolean checkPassword(String str){
				boolean digit = false;
				boolean letter = false;
				char[] test = str.toCharArray();
				for (int i = 0; i < test.length; i++) {
					if (letter && digit) {
						break;
					}
					if (Character.isLetter(test[i])) {
						letter = true;
					}
					if (Character.isDigit(test[i])) {
						digit = true;
					}
				}
				return digit && letter;
			}
			
}
