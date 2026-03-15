package carnetdecontacts.crypto;

import java.util.Objects;

import carnetdecontacts.crypto.keys.VigenereKey;
import carnetdecontacts.crypto.strategy.EncodingStrategy;

public final class VigenereCipherService implements Cipher {
	
	private EncodingStrategy encodingStrategy = null;
	private String keyString = null;
	@SuppressWarnings("unused")
	private int keyLength =-1;
	
	

	public VigenereCipherService(EncodingStrategy encodingStrategy) {
		this.encodingStrategy = Objects.requireNonNull(encodingStrategy);
	}
	
	private void validateMessage(String message) {
		if(!encodingStrategy.isMessageValid(message))
			throw new CryptoException("Erreur V01: Le message ne peut pas être null et doit appartenir au domaine de l'encodage choisi.");
		
	}
	
	private void readKey(Object[] objKey) {
		if(objKey == null || objKey.length!=1)
			throw new CryptoException("Erreur V02: La clé ne peut pas être null et doit contenir une clé valide.");
		
		if(!(objKey[0] instanceof VigenereKey))
			throw new CryptoException("Erreur V03: La clé doit être du type VigenereKey.");
		
		VigenereKey key = (VigenereKey)objKey[0];
		this.keyString= key.getKeyString();
		this.keyLength = this.keyString.length();
		
	}
	
	
	private String transform(String message,boolean encryptMode) {
		StringBuilder sb = new StringBuilder();
		int keyIndex = 0;
		int newPos = -1;
		
		for(int i = 0;i<message.length();i++) {
			char c = message.charAt(i);
			int msgPos = encodingStrategy.toIndex(c);
			int keyPos = encodingStrategy.toIndex(this.keyString.charAt(keyIndex%keyLength));
			
			newPos = encryptMode
					? encodingStrategy.normalize(msgPos+keyPos)
				    : encodingStrategy.normalize(msgPos-keyPos);
			keyIndex++;
			sb.append(encodingStrategy.toChar(newPos));
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
