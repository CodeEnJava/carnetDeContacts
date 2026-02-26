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
 *       Pour la prochaine vidéo prévoir la possibilité de choisir l'encodage
 *       
 *       ASCII 32 - 126 
 *       ALPHABETNUMBER
 *       UNICODE
 *      
 */
public class ArithmeticSerieCipherService implements Cipher {
	
	private int a;
	private int d;
	
	
	// les modifications
	private static final int UNICODE_RANGE = 65_536;

	public ArithmeticSerieCipherService(int a, int d) {
		super();
		this.a = a;
		this.d = d;
	}
	
	
	@Override
	public String encrypt(String message, String ...key) {
		ValidatMessage(message);
		return transform( message,  true);
	}

	
	@Override
	public String decrypt(String message, String ...key) {
		ValidatMessage(message);
		return transform( message,  false);
	}
	
	/*
	 * Analyse mathématique 
	 * 
	 * Question : Existe-t-il une limite pour a et d ?
	 * 
	 * Réponse Est OUI
	 * 
	 * Le problème ne vient pas des mathématiques :  Un = a + n*d
	 * 
	 * 
	 * Le problème vient des type int et char
	 * 
	 * La première limite: Provoque un OVERFLOW sur le calcul de shift
	 * 
	 * dans le code:
	 * int shift = a+ i*d;
	 * pour rappel un type int est un type signé qui occupe 4 octets (32 bits)
	 * la valeur est comprise :        -2_147_483_648 <= i <= 2_147_483_647
	 * 
	 * le produit i*d peut provoquer rapdiement un OVERFLOW
	 * 
	 * dans l'exemple:
	 *  int a = +2_100_000;
	 *	int d =+2_100_000_000; 
	 *  on lit le premier caractère: i = 1
	 *  shift = 2_100_000 + 2_100_000_000 = 2_102_100_000  il n'y a pas de dépassement (Overflow)
	 *  on lit le second caractère: i =2
	 *  shift = 2_100_000 + 2_100_000_000*2 = 4_202_100_000  --> il y a dépassement et la valeur de shift <0
	 *  
	 *  Dépassement de capacité
	 *  Provoque un OVERFLOW
	 *  Valeur négative attendue
	 *  
	 *  il faut 
	 *  |a + i*d | <= 2_147_483_647
	 *  SINON le résultat de shift devient incohérent
	 *  
	 * La sconde limite: Provoque un OVERFLOW sur le type char
	 * 
	 * Rappel le type char est non signé et occupe 2 octets soit 16bits
	 * sa valeur doit être comprise entre  0 <= c <= 65_535
	 * 
	 * dans le code:
	 * chars[i] = (char)(chars[i]+shift*sens);
	 * 
	 * si chars[i]+shift > 65_535 ou chars[i]+shift < 0
	 * 
	 * Mécanisme interne : utilisation d'un cast (char)  la JVM va réaliser un WRAP MODULO 65_536
	 * 
	 * Résultat :
	 *  des caractères illisibles
	 *  Perte de sens UNICODE
	 *  Mais, réversible si le même overflow se produit au dechiffrement , on n'a pas la garantie
	 *  
	 *  Est-ce que le déchiffrement est reversible ?
	 *  D'un point de vue mathémtique OUI, tant le même calcul est refait, overflow seproduit de manière identique
	 *  
	 *  Conclusion
	 *  
	 *  Ce n'est plus maitrisé
	 *  Ce n'est plus lisible
	 *  Ce n'est plus déterministe sur le plan conceptuel. Ici, on dépend du comportement interne des
	 *  types primitifs int et char
	 *  
	 *  Que faire pour éviter l'overflow 
	 *  
	 *  Soit L = la longueur maximal du message
	 *  
	 *  a + (L-1)*d <= Integer.MAX_VALUE
	 *  
	 *  la raison : d <= (Integer.MAX_VALUE -a)(L-1)
	 *  
	 *  Sur notre exemple:
	 *  
	 *  int a = +2_100_000;
	 *	int d =+2_100_000_000; --> on est hors limites
	 *  le message à une longueur de 100 caractères
	 *  
	 *  d = (2_147_483_647-2_100_000)/(99)
	 *  d # 21_670_542
	 *  
	 *  Bilan des limites critiques
	 *  
	 *  |a + i*d | <= Integer.MAX_VALUE
	 *  
	 *  chars[i]+shift <= 65_535 
	 *  chars[i]+shift >= 0
	 *  
	 *  
	 *  Comment faire pour rendre ROBUSTE et Sécurisée cette classe
	 *  
	 *  On va travaillé avec le modulo pour Unicode
	 *  
	 *  Cn = (Mn + a + n*d)modulo 65536
	 *  Mn = (Cn - a - n*d)modulo 65536
	 *  
	 *  
	 */
	private String transform(String message, boolean encryptMode) {
		
		char [] chars = message.toCharArray();
		
		
		for(int i=0;i<chars.length;i++) {
			long shift = (long)a+ (long)(i*d);  //pour éviter les dépassements intermédiaires
			long value = encryptMode
					     ? chars[i] + shift
					     : chars[i] - shift;
			chars[i] = normalizeToUnicode(value);
		}
		return new String(chars);
	}



	private char normalizeToUnicode(long value) {
		long mod = value % UNICODE_RANGE;
		if(mod<0)
			mod +=UNICODE_RANGE;
		return (char)mod;
	}

	private void ValidatMessage(String message) {
		if(message == null)
			throw new CryptoException("Erreur 10: Le message ne peut pas être null");
		
	}

}
