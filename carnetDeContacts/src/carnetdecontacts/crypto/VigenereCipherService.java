package carnetdecontacts.crypto;

/**
 * Implémentation du chiffrement de Vigenère.
 *
 * <p>Le chiffrement de Vigenère est un algorithme
 * poly-alphabétique utilisant une clé textuelle.
 * Chaque caractère du message est décalé selon
 * la position du caractère correspondant dans la clé.</p>
 *
 * <p>Exemple :</p>
 * <pre>
 * Message : BONJOUR
 * Clé     : CODE
 * Résultat: DCQNQIU
 * </pre>
 *
 * <p>L’algorithme applique un modulo sur la longueur
 * de l’alphabet pour assurer un chiffrement circulaire.</p>
 */
public class VigenereCipherService implements Cipher {

	@Override
	public String encrypt(String message, Object ...key) {
		return encryptDecrypt(message,  true, key);
	}

	@Override
	public String decrypt(String message, Object ...key) {
		return encryptDecrypt(message,  false,key);
	}
	
	
	/*
	 * INDEX    =  0  1  2  3  4  5  6  7  8  9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25
	 * ALPHABET = "A  B  C  D  E  F  G  H  I  J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z"
	 * 
	 * MESSAGE  = "BONJOUR LES CODEURS "
	 * INDEXKEY =  0 1 2 3
	 * KEY      = "C O D E"
	 * 
	 * On fixe indexKey = 0
	 * On lit le premier caractère du message :   B ,on va appliquer un décallage vers la droite de (La lettre C --> +2)
	 *                                        --> D, on incremente indexKey 1
	 * On lit le second caractère du message  :   O, on va appliquer un décallage vers la droite de ( la lettre O --> +14)
	 * 											  C ( on doit mettre en place le modulo) on incrémente indexKey 2
	 * On lit le troisième caractère du message : N on va appliquer un décallage vers la droite de ( la lettre D --> +3) 
	 *                                    		  Q on incrémente indexKey 3
	 * On lit le quatrième caractère du message : J on va appliquer un décallage vers la droite de ( la lettre E --> +4)                                   	
	 *                                            N on incrémente indexKey, on utilise aussi un modulo, 0
	 * On lit le cinquième caractère du message : O on va appliquer un décallage vers la droite de (La lettre C --> +2)  
	 * 											  Q  
	 * 
	 *  String message ="BONJOUR";
	 *  String key = "CODE";
	 *  CODECODE
	 *  DCQNQIU
	 *  DCQNQIU
	 */
	
	
	
	private boolean keyAuthorized(String key) {
		for(int i=0;i<key.length();i++) {
			char c = key.charAt(i);
			if(AlphabetCipher.CHAR_INDEX_MAP.get(c)==null)
				return false;
		 }
		return true;
	}
	
	/**
	 * Méthode interne factorisant la logique de chiffrement
	 * et de déchiffrement.
	 *
	 * @param message message à traiter
	 * @param key clé textuelle
	 * @param encryptDecrypt true pour chiffrer, false pour déchiffrer
	 * @return message transformé
	 */
	private String encryptDecrypt(String message,boolean encryptMode,Object ...objkey) {
		//prévoir la gestion des erreurs....
		ValidateInput( message, objkey) ; 
		
		String key = (String)objkey[0];
		// fin gestion des erreurs
		StringBuilder sb = new StringBuilder();
		int keyIndex = 0; // On se place sur le premier caractère de la clé
		int newPos = -1;
		
		for(int i=0;i<message.length();i++) {
			char c = message.charAt(i);
			
			if(AlphabetCipher.CHAR_INDEX_MAP.get(c)!=null && 
					AlphabetCipher.CHAR_INDEX_MAP.get(key.charAt(keyIndex%key.length()))!=null) {
				int msgPos = AlphabetCipher.CHAR_INDEX_MAP.get(c);
				
				int keyPos = AlphabetCipher.CHAR_INDEX_MAP.get(key.charAt(keyIndex%key.length()));
				
				newPos = encryptMode
									?(msgPos+keyPos)%AlphabetCipher.LENGTH
									:(msgPos-keyPos+AlphabetCipher.LENGTH)%AlphabetCipher.LENGTH;
				
				keyIndex++;
				sb.append(AlphabetCipher.CUSTOM_ALPHABET_NUMBER.charAt(newPos));
			}else
				sb.append(c);
			
		}
		return sb.toString();
		
	}
	
	private void ValidateInput(String message, Object ...key) {
		if(message == null)
			throw new CryptoException("Erreur 05 :\nLe paramétre contenant le message à chiffrer ne peut pas être null.");
		
		if(key == null || key.length==0)
			throw new CryptoException("Erreur 06 :\nLa clé est obligatoire.");
		
		if(key[0] == null )
			throw new CryptoException("Erreur 07 :\nLa clé ne peut pas être null.");
		
		if(!keyAuthorized((String)key[0]))
			throw new CryptoException("Erreur 08 :\nUtiliser uniquement des caractères se trouvant dans la map..."); 
	}

}
