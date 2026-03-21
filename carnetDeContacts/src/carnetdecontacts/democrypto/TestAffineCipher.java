package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.AffineCipherService;
import carnetdecontacts.crypto.keys.AffineKeys;
import carnetdecontacts.crypto.strategy.AsciiPrintableEncoding;
import carnetdecontacts.crypto.strategy.CustomAlphabetEncoding;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;

public class TestAffineCipher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String message ="Bienvenue dans ma chaine CodeEnJava...\n pour travailler des projets en JAVA  et coder...";
		
		System.out.println(message);
		
		testUnicode(message);
		
		testCustom(message);
		message = "Bonjour les codeurs JAVA";
		testASCII(message);
	
	}
	
	
	private static void testUnicode(String message) {
		EncodingStrategy encodingStrategy = new UnicodeEncoding();
		int[] keys = AffineKeys.keyRandomGenerator(encodingStrategy.domaineSize());
		
		int a = keys[0];
		int b = keys[1];
		int k = keys[2];
		
		System.out.println("Encodage : Unicode");
		test(message,encodingStrategy,a,b,k);
		System.out.println("---------------------------------");
	}
	
	
	private static void testCustom(String message) {
		EncodingStrategy encodingStrategy = new CustomAlphabetEncoding();
		int[] keys = AffineKeys.keyRandomGenerator(encodingStrategy.domaineSize());
		
		int a = keys[0];
		int b = keys[1];
		int k = keys[2];
		
		System.out.println("Encodage : CustomAlphabetEncoding");
		test(message,encodingStrategy,a,b,k);
		System.out.println("---------------------------------");
	}
	
	private static void testASCII(String message) {
		EncodingStrategy encodingStrategy = new AsciiPrintableEncoding();
		int[] keys = AffineKeys.keyRandomGenerator(encodingStrategy.domaineSize());
		
		int a = keys[0];
		int b = keys[1];
		int k = keys[2];
		
		System.out.println("Encodage : AsciiPrintableEncoding");
		
		
		test(message,encodingStrategy,a,b,k);
		System.out.println("---------------------------------");
	}
	
	
	private static void test(String message,EncodingStrategy encodingStrategy,int a, int b,int k) {
		
		AffineCipherService acs = new AffineCipherService(encodingStrategy);
		
		AffineKeys keys = new AffineKeys(a,b,k,encodingStrategy);
		
		String crypt = acs.encrypt(message, keys);
		System.out.println(crypt);
		
		String decrypt = acs.decrypt(crypt, keys);
		
		System.out.println(decrypt);
		
		System.out.println("Chiffrage/Déchiffrage réalisé ?"+message.equals(decrypt));
		
	}

}
