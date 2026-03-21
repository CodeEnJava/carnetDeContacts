package carnetdecontacts.crypto;

import java.util.Objects;

import carnetdecontacts.crypto.keys.ArithmeticKeys;
import carnetdecontacts.crypto.keys.CesarKey;
import carnetdecontacts.crypto.strategy.EncodingStrategy;

/*
 * Le principe , c'est utiliser sur série Arithmétique
 * 
 * Un = a + n*d
 * 
 * 
 * avec :
 *       a : qui représente la valeur de départ (Offset initial)
 *       d : la raison ( le pas )
 *       n : la position du caractère dans le message à crypter ou décrypter
 *       
 *       Crypter:
 *       charChiffre = charMessage + Un
 *       
 *       décrypter:
 *       
 *       charMessage = charChiffre - Un
 *       
 *        
 *      
 */
public class ArithmeticSerieCipherService implements Cipher {
	
	//pour éviter les dépassements intermédiaires utilisation du type long
	private long a;
	private long d;
	
	
	private EncodingStrategy encodingStrategy = null;

	/*
	 * Modification du construction pour permettre l'inection de dépendance
	 */
	public ArithmeticSerieCipherService(EncodingStrategy encodingStrategy) {
		super();
		/*
		 * Ce constructeur respect le principe d'inversion de dépendance
		 * Il sera plus facile de réaliser des tests unitaires.
		 * la classe est totalement découplée.
		 */
		
		this.encodingStrategy = Objects.requireNonNull(encodingStrategy);
		
	}
	
	
	@Override
	public String encrypt(String message, Object ...key) {
		ValidatMessage(message);
		readKeys(key);
		return transform( message,  true);
	}

	
	@Override
	public String decrypt(String message, Object ...key) {
		ValidatMessage(message);
		readKeys(key);
		return transform( message,  false);
	}
	
	/*
	 * Ajouter une méthode pour lire les parametres de la clé
	 */
	private void readKeys(Object[] key) {
		if(key==null || key.length!=1)
			throw new CryptoException("La clé ne peut pas être null et doit contenir une clé valide.");
		
		if(!(key[0] instanceof ArithmeticKeys))
			throw new CryptoException("Erreur xx: La clé doit être du type ArithmeticKeys.");
		
		ArithmeticKeys keys = (ArithmeticKeys)key[0];
		this.a = keys.getA();
		this.d = keys.getD();
		
	}
	
	/*
	 * Modification de la méthode pour prendre en compte le pattern strategy
	 */
	private String transform(String message, boolean encryptMode) {
		
		char [] chars = message.toCharArray();
		
		for(int i=0;i<chars.length;i++) {
			
			long u = a+ (i*d);
			int index = encodingStrategy.toIndex(chars[i]);
			
			long shifted = encryptMode
					     ? encodingStrategy.normalize(index+u)
					     : encodingStrategy.normalize(index-u);
			
			chars[i] = encodingStrategy.toChar(shifted);
		}
		return new String(chars);
	}


	/*
	 * Modification pour utiliser la méthode isMessage() de la classe EncodingStrategy
	 */
	private void ValidatMessage(String message) {
		if(!encodingStrategy.isMessageValid(message))
			throw new CryptoException("Erreur 10: Le message ne peut pas être null et doit appartenir au domaine de l'encodage choisi.");
		
	}

}
