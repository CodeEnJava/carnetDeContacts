package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.VigenereCipherService;
import carnetdecontacts.crypto.keys.VigenereKey;
import carnetdecontacts.crypto.strategy.AsciiPrintableEncoding;
import carnetdecontacts.crypto.strategy.CustomAlphabetEncoding;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;

public class TestVigenere {

	public static void main(String[] args) {
		
		String message ="Bonjour à tous,\nBienvenue sur cette vidéo mettant oeuvre le chiffrement de Vignère...";	
		String key = "A";
		
		testUnicode(message, key);
		key = "AB";
		testCustom(message, key);
		
		message = "Bonjour et bienvenue sur ma chaine CodeEn Java";
		key = "1235695";
		testAciiPrintable(message, key);
	}
	
	private static void testUnicode(String message, String key) {
		
		EncodingStrategy encoding = new UnicodeEncoding();
		System.out.println("Encodage :UnicodeEncoding");
		test( message,  encoding, key);
		
	}
	
	private static void testAciiPrintable(String message, String key) {
		
		EncodingStrategy encoding = new AsciiPrintableEncoding();
		System.out.println("Encodage :AsciiPrintableEncoding");
		test( message,  encoding, key);
		
	}
	
	private static void testCustom(String message, String key) {
		
		EncodingStrategy encoding = new CustomAlphabetEncoding();
		System.out.println("Encodage :CustomAlphabetEncoding");
		test( message,  encoding, key);
		
	}
	
	
	private static void test(String message, EncodingStrategy encoding,String strkey) {
		VigenereCipherService vcs = new VigenereCipherService(encoding);
		
		VigenereKey key = new VigenereKey(strkey, encoding);
		
		String encrypt = vcs.encrypt(message, key);
		System.out.println("message crypté :\n"+encrypt);
		
		String decrypt = vcs.decrypt(encrypt, key);
		System.out.println("message décrypté :\n"+decrypt);
		
		System.out.println("Valide ?"+(message.equals(decrypt)));
		System.out.println("------------------------------------");
	}

}
