package carnetdecontacts.crypto_old;

import carnetdecontacts.crypto.AlphabetCipher;
import carnetdecontacts.crypto.Cipher;
import carnetdecontacts.crypto.CryptoException;

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
public class CesarCipherService_old implements Cipher {
	
	

	public CesarCipherService_old() {
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
	public String encrypt(String message, Object ...key) {
		// si key > 0 décalage vers la droite sinon décalage vers la gauche
		//Prévoir la gestion des erreurs
		validateInputs( message,key);
		// fin de la gestion des erreurs
		
		int shift = Integer.parseInt((String)key[0]);
		shift %= AlphabetCipher.LENGTH;
		StringBuilder sb = new StringBuilder();
		
		for(char c:message.toCharArray()) {
			if(AlphabetCipher.CHAR_INDEX_MAP.get(c)!=null) {
				int pos = (AlphabetCipher.CHAR_INDEX_MAP.get(c)+shift)% AlphabetCipher.LENGTH;
				//System.out.println("pos ="+pos);
				if(pos<0)
					pos = AlphabetCipher.LENGTH+pos;
				sb.append(AlphabetCipher.CUSTOM_ALPHABET_NUMBER.charAt(pos));
			}else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

	@Override
	public String decrypt(String message, Object ...key) {
		validateInputs( message,key);
		int intKey = Integer.parseInt((String)key[0]);
		return encrypt(message, String.valueOf(AlphabetCipher.LENGTH-intKey));
	}
	
	private void validateInputs(String message, Object ...key) {
		if(message == null)
			throw new CryptoException("Erreur 01 :\nLe paramétre contenant le message à chiffrer ne peut pas être null.");
		
		if(key == null || key.length == 0)
			throw new CryptoException("Erreur 02 :\nla clé est obligatoire.");
		
		if(key[0] == null ) 
			throw new CryptoException("Erreur 03 :\nla clé ne peut pas être null.");
		
		
		if(!isNumber((String)key[0])) 
			throw new CryptoException("Erreur 04 :\nLe paramétre contenant la clé doit contenir un nombre entier.");
		
	}

}
