package carnetdecontacts.crypto;
/*
 * Le principe , c'est utiliser sur série Arithmétique
 * 
 * Un = a + n*d
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
 */
public class ArithmeticSerieCipherService implements Cipher {
	
	private int a;
	private int d;
	
	

	public ArithmeticSerieCipherService(int a, int d) {
		super();
		this.a = a;
		this.d = d;
	}

	@Override
	public String encrypt(String message, String key) {
		return encrypt( message,  true);
	}

	@Override
	public String decrypt(String message, String key) {
		return encrypt( message,  false);
	}
	
	
	private String encrypt(String message, boolean encryptDecrypt) {
		
		char [] chars = message.toCharArray();
		int sens = -1;
		if(encryptDecrypt)
			sens = 1;
		
		for(int i=0;i<chars.length;i++) {
			int shift = a+ i*d;
			chars[i] = (char)(chars[i]+shift*sens);
		}
		
		return new String(chars);
	}

}
