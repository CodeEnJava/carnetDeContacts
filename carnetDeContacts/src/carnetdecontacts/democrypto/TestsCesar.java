package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.CesarCipherService;

public class TestsCesar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String message = "Bonjour zes codeurs CodeEnJava";
		//test1(message);
		
		
		message = "@ étudier JAVA (JDK17 & JDK21)!! pour réussir des projets solide !";
		test1(message);
		
	}
	
	private static void test1(String message) {
CesarCipherService  ccs = new CesarCipherService();
		
		String crypte = ccs.encrypt(message, "5");
		System.out.println(crypte);
		//  un petit bug
		// -1-1-1-1-1-1-1 -1-1-1 -1-1-1-1-1-1-1 -1-1-1-1-1-1-1-1-1-1
		// après le debug
		// Gtsotzw Ejx htijzwx HtijJsOfAf
		
		
		String decripte = ccs.decrypt(crypte, "5");
		
		System.out.println(decripte);
		
		System.out.println("codage/decodage réussi ?"+(message.equals(decripte)));
		
	}

}
