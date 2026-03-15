package carnetdecontacts.crypto.keys;

import carnetdecontacts.crypto.AlphabetCipher;
import carnetdecontacts.crypto.CryptoException;
import carnetdecontacts.crypto.strategy.AsciiPrintableEncoding;
import carnetdecontacts.crypto.strategy.CustomAlphabetEncoding;
import carnetdecontacts.crypto.strategy.EncodingStrategy;

public final class VigenereKey {
	
	private final String keyString;

	public VigenereKey(String keyString,EncodingStrategy encodingStrategy) {
		if(!encodingStrategy.isMessageValid(keyString))
			throw new CryptoException("Erreur VigenereKey: L'encodage de la clé n'est pas valide.");
		if(keyString.length()==0)
			throw new CryptoException("Erreur VigenereKey: La clé ne peut  pas être une chaine vide.");
		
		if (encodingStrategy instanceof CustomAlphabetEncoding  
				&& keyString.equals(""+AlphabetCipher.CUSTOM_ALPHABET_NUMBER.charAt(0)))
			throw new CryptoException("Erreur VigenereKey: "
					+ "La clé ne peut être composé uniquement du caractère ou d'une séquence :"
				   +AlphabetCipher.CUSTOM_ALPHABET_NUMBER.charAt(0));
		// prévoir une fonction qui vérifie que la séquence n'est pas périodique, dans le future.
		// prochainement.
		if (encodingStrategy instanceof CustomAlphabetEncoding  
				&& !isPreriodic(keyString))
			throw new CryptoException("Erreur VigenereKey: "
					+ "La clé ne ne peut pas être composée d'une chaine périodique");
		
		
		if (encodingStrategy instanceof AsciiPrintableEncoding  
				&& keyString.equals(""+(char)AlphabetCipher.ASCII_BEGIN))// le caractère de code ASCII 32
			throw new CryptoException("Erreur VigenereKey: "
					+ "La clé ne peut être composé uniquement du caractère ou d'une séquence :"
				   +(char)AlphabetCipher.ASCII_BEGIN);
		
		// prévoir une fonction qui vérifie que la séquence n'est pas périodique, dans le future.
		// prochainement.
		
		if (encodingStrategy instanceof AsciiPrintableEncoding  
				&& !isPreriodic(keyString))
			throw new CryptoException("Erreur VigenereKey: "
					+ "La clé ne ne peut pas être composée d'une chaine périodique");
		this.keyString = keyString;
	}
	

	public String getKeyString() {
		return keyString;
	}
	
	
	private boolean isPreriodic(String strKey) {
		// TODO
		return true;
	}

}
