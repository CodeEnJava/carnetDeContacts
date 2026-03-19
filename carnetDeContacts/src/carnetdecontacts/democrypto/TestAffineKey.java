package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.keys.AffineKeys;
import carnetdecontacts.crypto.strategy.AsciiPrintableEncoding;
import carnetdecontacts.crypto.strategy.CustomAlphabetEncoding;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;

public class TestAffineKey {

	public static void main(String[] args) {
		
		
		int a = 10120;
		int b = -80;
		
		EncodingStrategy encodingStrategy = new AsciiPrintableEncoding();
		
		int[] keys = AffineKeys.keyRandomGenerator(encodingStrategy.domaineSize());
		
		System.out.println("a = "+keys[0]);
		
		System.out.println("b = "+keys[1]);
		
		System.out.println("k = "+keys[2]);
		
		a = keys[0];
		b = keys[1];
		int k = keys[2];
		
		AffineKeys affinekeys = new AffineKeys(a, b,k, encodingStrategy);
		
		System.out.println("inverse modulaire :"+affinekeys.getInverseModulaire());
		
	}

}
