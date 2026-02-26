package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.ArithmeticSerieCipherService;
import carnetdecontacts.crypto.Cipher;

public class TestArithmeticSerie {

	public static void main(String[] args) {
		int a = +2_100_000;
		int d =+2_100_000_000;            
		Cipher ascs = new ArithmeticSerieCipherService(a,d );
		
		String message ="Bonjour à tous,\nBienvenue sur cette vidéo mettant oeuvre "
				      + "le chiffrement avec une série arithmétique...";
		System.out.println(message);
		System.out.println();
		String crypte = ascs.encrypt(message);
		System.out.println(crypte);
		System.out.println();
		String decrypte = ascs.decrypt(crypte);
		System.out.println(decrypte);
		
		System.out.println("résussi ?"+message.equals(decrypte));
		
	}

}
