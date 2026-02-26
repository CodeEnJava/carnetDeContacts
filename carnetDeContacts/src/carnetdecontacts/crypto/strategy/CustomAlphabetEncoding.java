package carnetdecontacts.crypto.strategy;

import carnetdecontacts.crypto.AlphabetCipher;
import carnetdecontacts.crypto.CryptoException;


/*
 * La classe est déclarée final, cela signifie qu'elle ne peut pas être héritée.
 * Cela renforce la stabilité et la sécurité de l'implémentation.
 * La prévisilibité est importante dans le domaine de la cryptographie
 */
public final class CustomAlphabetEncoding implements EncodingStrategy {
	
	public CustomAlphabetEncoding() {
		super();
		
	}

	@Override
	/*
	 * Comme pour toute stratégie de chiffrement par décalage, nous devons utiliser
	 * l'opérateur modulo (mathématique) afin de rester dans le domaine valide
	 */
	public int normalize(long value) {
		// il est préférable d'utiliser floorMod que l'opérateur % pour garantir 
		// un comportement correct, même pour les valeurs négatives.
		
		return Math.floorMod(value, AlphabetCipher.LENGTH);
	}

	@Override
	public int domaineSize() {
		return AlphabetCipher.LENGTH;
	}

	@Override
	public int toIndex(char c) {
		if(!isCharValid(c))
			throw new CryptoException("Caractère en dehors du domaine personnalisé");
		return AlphabetCipher.CHAR_INDEX_MAP.get(c);
	}

	@Override
	public char toChar(long index) {
		int normalized = normalize(index);
		return AlphabetCipher.CUSTOM_ALPHABET_NUMBER.charAt(normalized);
	}

	@Override
	public boolean isCharValid(char c) {
		/*
		 * Permet de vérifier si un caractère appartient au domaine
		 * Vérification rapide.
		 */
		return AlphabetCipher.CHAR_INDEX_MAP.containsKey(c);
		
	}

	@Override
	/*
	 * Objectif est de vérifier si l'ensemble des caractères utilisés 
	 * dans le message appartient au domaine
	 */
	public boolean isMessageValid(String message) {
		if(message == null)
			return false;
		for(int i=0;i<message.length();i++) {
			char c = message.charAt(i);
			if(!isCharValid(c))
				return false;
		}
		return true;
	}
	
	/*
	 * Comparaison entre UNICODE Et CUSTOM
	 * 
	 * Contrairemment à UnicodeStrategy:
	 * 	--> Restreint le domaine
	 *  --> Enpêche les caractères non définit
	 *  --> Utilise une table d'indexation
	 *  
	 *  Elle est plus stricte mais plus controlée
	 *  
	 *  Unicodexxxx = Flexibilité maximale
	 *  Customxxxx  = Sécurité et cohérence maximale
	 *  
	 */

}
