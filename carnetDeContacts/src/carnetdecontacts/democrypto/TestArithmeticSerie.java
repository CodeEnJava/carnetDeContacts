package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.ArithmeticSerieCipherService;

public class TestArithmeticSerie {

	public static void main(String[] args) {
		
		ArithmeticSerieCipherService ascs = new ArithmeticSerieCipherService(15, 25);
		
		String message ="Bonjour à tous,\nBienvenue sur cette vidéo mettant oeuvre le chiffrement avec une série arithmétique...";
		System.out.println(message);
		System.out.println();
		String crypte = ascs.encrypt(message, null);
		System.out.println(crypte);
		System.out.println();
		String decrypte = ascs.decrypt(crypte, null);
		System.out.println(decrypte);
		
		System.out.println("résussi ?"+message.equals(decrypte));
		
		
		//ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	}

}
