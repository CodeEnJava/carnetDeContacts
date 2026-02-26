package carnetdecontacts.crypto.strategy;

import carnetdecontacts.crypto.AlphabetCipher;

public final class UnicodeEncoding implements EncodingStrategy {
	
	
	public UnicodeEncoding() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int normalize(long value) {
		/*
		 * Utilisation de floorMod, garantit la robustesse du chiffrement  même en cas de 
		 * décalage négatif
		 */
		return Math.floorMod(value, AlphabetCipher.UNICODE_RANGE);
	}

	@Override
	public int domaineSize() {
		
		return AlphabetCipher.UNICODE_RANGE;
	}

	@Override
	public int toIndex(char c) {
		/*
		 * En unicode, un char est déjà une valeur numérique
		 * Donc ici la conversion est direct
		 */
		
		return c;
	}

	@Override
	public char toChar(long index) {
		return normalizeToUnicode(index);
	}

	@Override
	public boolean isCharValid(char c) {
		return true;
	}

	@Override
	public boolean isMessageValid(String message) {
		if(message == null)
			return false;
		return true;
	}
	
	private char normalizeToUnicode(long value) {
		/*
		 * Je normalise le paramère value, pour garantire
		 * que celui-ci reste dans le domaine
		 * 
		 */
		long mod = normalize( value);
		if(mod <0)
			mod += AlphabetCipher.UNICODE_RANGE;
		return (char) mod;
	}

}
