package carnetdecontacts.crypto;
/**
 * Implémentation du chiffrement de César.
 *
 * <p>Le chiffrement de César est un algorithme de substitution
 * mono-alphabétique basé sur un décalage circulaire
 * des caractères dans l’alphabet.</p>
 *
 * <p>Exemple :
 * clé = 3
 * A → D
 * B → E
 * </p>
 *
 * <p>La clé peut être positive (décalage à droite)
 * ou négative (décalage à gauche).</p>
 */
public class CesarCipherService implements Cipher {
	
	

	public CesarCipherService() {
		super();
		
	}
	
	/**
	 * Chiffre un message en appliquant un décalage circulaire.
	 *
	 * @param message message à chiffrer
	 * @param key clé numérique représentant le décalage
	 * @return message chiffré
	 * 
	 */
	private boolean isNumber(String num) {
		try {
			@SuppressWarnings("unused")
			int n = Integer.parseInt(num);
			return true;
		}catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	/**
	 * Déchiffre un message chiffré avec l’algorithme de César.
	 *
	 * @param message message chiffré
	 * @param key clé numérique utilisée lors du chiffrement
	 * @return message original
	 */
	@Override
	public String encrypt(String message, String key) {
		// si key > 0 décalage vers la droite sinon décalage vers la gauche
		//Prévoir la gestion des erreurs
		if(message == null)
			throw new CryptoException("Erreur 01 :\nLe paramétre contenant le message à chiffrer ne peut pas être null.");
		
		if(key == null)
			throw new CryptoException("Erreur 02 :\nLe paramétre contenant la clé ne peut pas être null.");
		
		if(!isNumber(key))
			throw new CryptoException("Erreur 03 :\nLe paramétre contenant la clé doit contenir un nombre entier.");
		
		// fin de la gestion des erreurs
		
		int shift = Integer.parseInt(key);
		shift %= AlphabetCipher.LENGTH;
		StringBuilder sb = new StringBuilder();
		
		for(char c:message.toCharArray()) {
			if(AlphabetCipher.CHARINDEXMAP.get(c)!=null) {
				int pos = (AlphabetCipher.CHARINDEXMAP.get(c)+shift)% AlphabetCipher.LENGTH;
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
