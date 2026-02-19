package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.VigenereCipherService;

public class TestVigenere {

	public static void main(String[] args) {
		VigenereCipherService vcs = new VigenereCipherService();
		
		String message ="Bonjour à tous,\nBienvenue sur cette vidéo mettant oeuvre le chiffrement de Vignère...";
		String key = "@#code01!?#@";
		System.out.println(message);
		
		String crypt = vcs.encrypt(message, key);
		
		System.out.println(crypt);
		
		String decrypt = vcs.decrypt(crypt, key);
		System.out.println(decrypt);
		System.out.println("réussite ?"+(message.equals(decrypt)));

	}

}
