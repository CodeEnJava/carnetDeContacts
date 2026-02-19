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
 * ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
 * key = -5
 * 
 * String message = "Bonjour zes codeurs CodeEnJava";
 * 					 wji.....
 * 			         
 */
public class CesarCipherService implements Cipher {
	
	

	public CesarCipherService() {
		super();
		
	}
	
	private boolean isNumber(String num) {
		try {
			@SuppressWarnings("unused")
			int n = Integer.parseInt(num);
			return true;
		}catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	@Override
	public String encrypt(String message, String key) {
		// si key > 0 décallage vers la droite sinon décallage vers la gauche
		//Prévoir la gestion des erreurs
		if(message == null)
			throw new RuntimeException("Erreur 01 :\nLe paramétre contenant le message à chiffrer ne peut pas être null.");
		
		if(key == null)
			throw new RuntimeException("Erreur 02 :\nLe paramétre contenant la clé ne peut pas être null.");
		
		if(!isNumber(key))
			throw new RuntimeException("Erreur 03 :\nLe paramétre contenant la clé doit contenir un nombre entier.");
		
		// fin de la gestion des erreurs
		
		int shift = Integer.parseInt(key);
		shift %= AlphabetCipher.LENGTH;
		StringBuilder sb = new StringBuilder();
		
		for(char c:message.toCharArray()) {
			if(AlphabetCipher.ALPHABETNUMBER.indexOf(c)!= -1) {
				int pos = (AlphabetCipher.ALPHABETNUMBER.indexOf(c)+shift)% AlphabetCipher.LENGTH;
				//System.out.println("pos ="+pos);
				if(pos<0)
					pos = AlphabetCipher.LENGTH+pos;
				sb.append(AlphabetCipher.ALPHABETNUMBER.charAt(pos));
			}else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

	@Override
	public String decrypt(String message, String key) {
		int intKey = Integer.parseInt(key);
		return encrypt(message, String.valueOf(AlphabetCipher.LENGTH-intKey));
	}

}
