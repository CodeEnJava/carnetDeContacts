package carnetdecontacts.crypto.strategy;

import java.security.AlgorithmConstraints;

import carnetdecontacts.crypto.AlphabetCipher;
import carnetdecontacts.crypto.CryptoException;

public final class AsciiPrintableEncoding implements EncodingStrategy {
	
	/*
	 * Le rôle du pattern Strategy
	 * 
	 * EncodingStrategy 
	 *    | -> UnicodeEncoding
	 *    | -> CustomAlphabetEncoding
	 *    | -> AsciiPrintableEncoding
	 *    
	 *    Grace au pattern Strategy
	 *    
	 *    Le chiffrement est indépendant du type d'encodage
	 *    On peut changer de domaine de transformation sans modifier l'algorithme
	 *    Le code reste propre, extensible et maintenable
	 *    
	 *    
	 *    AsciiPrintableEncoding est donc une nouvelle stratégie interchangeable
	 */
	

	public AsciiPrintableEncoding() {
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
				
		return Math.floorMod(value,AlphabetCipher.ASCII_RANGE);
	}

	@Override
	public int domaineSize() {
		return AlphabetCipher.ASCII_RANGE;
	}

	@Override
	public int toIndex(char c) {
		if(!isCharValid(c))
			throw new CryptoException("Caractère hors ASCII non accentué imprimable");
		return AlphabetCipher.CHAR_INDEX_MAP_ASCII.get(c);
	}

	@Override
	public char toChar(long index) {
		int normalized = normalize(index);
		return (char)(normalized+ AlphabetCipher.ASCII_BEGIN);
	}

	@Override
	public boolean isCharValid(char c) {
		/*
		 * Permet de vérifier si un caractère appartient au domaine
		 * Vérification rapide.
		 */
		return AlphabetCipher.CHAR_INDEX_MAP_ASCII.containsKey(c);
	}

	@Override
	/*
	 * Objectif est de vérifier si l'ensemble des caractères utilisés 
	 * dans le message appartient au domaine
	 */
	public boolean isMessageValid(String message) {
		if(message == null)
			return false;
		for(int i=0; i<message.length();i++) {
			char c = message.charAt(i);
			if(!isCharValid(c))
				return false;
		}
		return true;
	}
	
	/*
	 * La stratégies AsciiPrintableEncoding est un excellent compromis entre les deux autres stratégies implémentée 
	 * précédement.
	 * 
	 * Elle est compatible systèmes
	 * Adapté aux mot de passe
	 * Simple et perfomant
	 * 
	 * Exemple concret:
	 * 
	 * Message = "Hello123!"
	 * Chaque caractère:
	 *     --> converti en index
	 *     --> Transformé mathématiquement
	 *     --> Reconstruit  via ToChar()
	 *     
	 *     SI un caractère comme 'é' apparait, il y a une exception immédiate
	 *     
	 *  Avec AsciiPrintableEncoding nous avons,
	 *  --> une nouvelle stratégie interchangeable
	 *  --> Un domaine bien défini
	 *  --> Une validation robuste
	 *  --> une Achitecture propre
	 *  
	 *  C'est exactement l'objectif du pattern Strategy
	 *  Séparer le comportement variable du reste du systeme.
	 *  
	 *     
	 */

}
