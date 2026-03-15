package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.CesarCipherService;
import carnetdecontacts.crypto.keys.CesarKey;
import carnetdecontacts.crypto.strategy.AsciiPrintableEncoding;
import carnetdecontacts.crypto.strategy.CustomAlphabetEncoding;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;

public class TestsCesar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String message = "Bonjour zes codeurs CodeEnJava";
		//test1(message);
		
		
		message = "@ étudier JAVA (JDK17 & JDK21)!! pour réussir des projets solide !";
		System.out.println(message);
		testUnicode(message,12000);
		testCustom(message,-93);
		message ="Bienvenue sur ma chaine JAVA";
		testAsciiPrintable(message,2018);
	}
	
	private static void testUnicode(String message, int key) {
		EncodingStrategy encoding = new UnicodeEncoding();
		System.out.println("Encodage: UnicodeEncoding");
		test( message, encoding,key);
		System.out.println("----------------------------------");
	}
	
	private static void testAsciiPrintable(String message, int key) {
		EncodingStrategy encoding = new AsciiPrintableEncoding();
		System.out.println("Encodage: AsciiPrintableEncoding");
		test( message, encoding,key);
		System.out.println("----------------------------------");
	}
	
	private static void testCustom(String message, int key) {
		EncodingStrategy encoding = new CustomAlphabetEncoding();
		System.out.println("Encodage: CustomAlphabetEncoding");
		test( message, encoding,key);
		System.out.println("----------------------------------");
	}
	
	private static void test(String message,EncodingStrategy encoding,int intkey) {
		
		CesarCipherService  ccs = new CesarCipherService(encoding);
		
		CesarKey key = new CesarKey(intkey,encoding);
		
		String crypte = ccs.encrypt(message,key);
		System.out.println(crypte);
		
		String decripte = ccs.decrypt(crypte, key);
		
		System.out.println(decripte);
		
		System.out.println("codage/decodage réussi ?"+(message.equals(decripte)));
		
	}
	
	
}
