package carnetdecontacts.crypto;

import java.util.Objects;

import carnetdecontacts.crypto.keys.CesarKey;
import carnetdecontacts.crypto.strategy.EncodingStrategy;

public class CesarCipherService implements Cipher {
	
	private EncodingStrategy encodingStrategy = null;
	private int shift;
	
	

	public CesarCipherService(EncodingStrategy encodingStrategy) {
		this.encodingStrategy= Objects.requireNonNull(encodingStrategy);
	}
	
	private void validateMessage(String message) {
		if(!encodingStrategy.isMessageValid(message))
			throw new CryptoException("Erreur C01: Le message ne peut pas être null et doit appartenir au domaine de l'encodage choisi.");
		
	}
	
	private void readKey(Object[] objKey) {
		if(objKey == null || objKey.length!=1)
			throw new CryptoException("Erreur C02: La clé ne peut pas être null et doit contenir une clé valide.");
		
		if(!(objKey[0] instanceof CesarKey))
			throw new CryptoException("Erreur C03: La clé doit être du type CesarKey.");
		
		CesarKey key = (CesarKey)objKey[0];
		this.shift= key.getShift();
		
	}
	
	
	private String transform(String message,boolean encryptMode) {
		shift %=encodingStrategy.domaineSize();
		
		StringBuilder sb = new StringBuilder();
		
		for(char c: message.toCharArray()) {
			int pos = encodingStrategy.toIndex(c);
			int shifted = encryptMode
					? encodingStrategy.normalize(pos+shift)
				    : encodingStrategy.normalize(pos-shift);
			sb.append(encodingStrategy.toChar(shifted));
		}
		return sb.toString();
	}

	@Override
	public String encrypt(String message, Object... key) {
		validateMessage(message);
		readKey(key);
		return transform(message,true);
	}

	@Override
	public String decrypt(String message, Object... key) {
		validateMessage(message);
		readKey(key);
		return transform(message,false);
	}

}
