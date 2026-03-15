package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.ArithmeticSerieCipherService;
import carnetdecontacts.crypto.Cipher;
import carnetdecontacts.crypto.keys.ArithmeticKeys;
import carnetdecontacts.crypto.strategy.AsciiPrintableEncoding;
import carnetdecontacts.crypto.strategy.CustomAlphabetEncoding;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;

public class TestArithmeticSerie {

	public static void main(String[] args) {
		
		EncodingStrategy encoding = new UnicodeEncoding();
		
		int a =  5_000_000;
		int d =  1000;    
		
		ArithmeticKeys keys = new ArithmeticKeys(a, d,encoding.domaineSize());
		Cipher ascs = new ArithmeticSerieCipherService(encoding);
		
		String message ="Bonjour à tous,\nBienvenue sur cette vidéo mettant oeuvre "
				      + "le chiffrement avec une série arithmétique...";
		
		message = "Bonjour et bienvenue sur ma chaine CodeEnJava";
		System.out.println(message);
		System.out.println();
		String crypte = ascs.encrypt(message,keys);
		System.out.println(crypte);
		System.out.println();
		String decrypte = ascs.decrypt(crypte,keys);
		System.out.println(decrypte);
		
		System.out.println("résussi ?"+message.equals(decrypte));
		
		
		/*
		 * Existe-t-il un couple (a,d) tel que Encrypt(message) = message pour tout message valide?
		 * 
		 * pour chaque position i on a: u(i) = a + i*d
		 * avec u(i) qui est  la position du caractère contenu dans le message ( message en clair, message chiffré) dans l'alphabet utilisé
		 * 
		 * Pour un caractère d'index k
		 * k <- normalize(k+u(i))
		 * 
		 * Le message est identique si on a l'égalité mathématique suivante: normalize(k+u(i)) = k
		 * 
		 * cela implique que u(i) congru à 0 modulo N ( avec N = domaineSize() )
		 * 
		 * 
		 * soit le couple (a,0) 
		 * 
		 * u(i) = a + i*0 = a  -> u(i) congrue à 0 modulo N   -> a = k*N  avec k une entier relatif  quelconque
		 * 
		 * a congru à 0 modulo N
		 * Si on a ce cas, cela signifie que le chiffrage est neutre, il faut apporter un correctif dans la classe ArithmeticKeys
		 * 
		 * 
		 * u(i) = a + i*0 = a -> fonction constante
		 * Si u(i) ≡ 0 (mod N) alors a ≡ 0 (mod N)
		 * Donc a = k*N avec k ∈ Z 
		 * 
		 * il faut impérativement apporter un correctif à la classe ArithmeticKey pour la prise en compte du domaine 
		 * 
		 * * soit le couple (0,0) 
		 * u(i) = 0 + i*0 = 0 -> fonction constante
		 * Si u(i) ≡ 0 (mod N) alors 0 ≡ 0 (mod N)
		 * Donc 0 = 0*N avec k ∈ Z 
		 * 
		 * il faut impérativement apporter un correctif à la classe ArithmeticKey pour la prise en compte du domaine  OUI
		 * 
		 * a
		 */
	}

}
