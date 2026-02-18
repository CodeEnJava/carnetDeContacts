package carnetdecontacts.crypto;
/**
 * Explication du chiffrement de César
 * 
 * ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
 * key = 5
 * 
 * String message = "Bonjour zes codeurs CodeEnJava";
 * 			   Gtsotzw Ej......
 * 			   Gtsotzw Ejx
 * 
 * 
 */
public class CesarCipherService implements Cipher {
	
	private static final String ALPHABETMAJMIN =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private static final String ALPHABET = ALPHABETMAJMIN+
			"éèàçù@#&'(§!)}°-_/*+%$,;.<>`£ ";
	
	private static final String NUMBER ="0123456789";
	
	private static final String ALPHABETNUMBER = ALPHABET+NUMBER;
	private static final int length = ALPHABETNUMBER.length();

	public CesarCipherService() {
		super();
		
	}

	@Override
	public String encrypt(String message, String key) {
		//Prévoir la gestion des erreurs
		
		// fin de la gestion des erreurs
		int shift = Integer.parseInt(key);
		StringBuilder sb = new StringBuilder();
		for(char c:message.toCharArray()) {
			if(ALPHABETNUMBER.indexOf(c)!= -1) {
				int pos = (ALPHABETNUMBER.indexOf(c)+shift)% length;
				// bug
				// sb.append(ALPHABETMAJMIN.indexOf(pos));
				sb.append(ALPHABETNUMBER.charAt(pos));
			}else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

	@Override
	public String decrypt(String message, String key) {
		return encrypt(message, String.valueOf(length-Integer.parseInt(key)));
	}

}
