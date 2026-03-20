package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.strategy.AsciiPrintableEncoding;
import carnetdecontacts.crypto.strategy.CustomAlphabetEncoding;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;
import carnetdecontacts.crypto_old.AffineKeys_old;

public class TestAffineKey {

	public static void main(String[] args) {
		
		
		int a = 10120;
		int b = -80;
		
		EncodingStrategy encodingStrategy = new AsciiPrintableEncoding();
		
		int[] keys = AffineKeys_old.keyRandomGenerator(encodingStrategy.domaineSize());
		
		System.out.println("a = "+keys[0]);
		
		System.out.println("b = "+keys[1]);
		
		System.out.println("k = "+keys[2]);
		
		a = keys[0];
		b = keys[1];
		int k = keys[2];
		
		AffineKeys_old affinekeys = new AffineKeys_old(a, b,k, encodingStrategy);
		
		System.out.println("inverse modulaire :"+affinekeys.getInverseModulaire());
		
	}

}
