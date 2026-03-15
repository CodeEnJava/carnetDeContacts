package carnetdecontacts.crypto.keys;

import carnetdecontacts.crypto.CryptoException;
import carnetdecontacts.crypto.strategy.EncodingStrategy;

public final class CesarKey {

	
	private final int shift;

	public CesarKey(int shift,EncodingStrategy encodingStrategy) {
		if(shift==0 || shift%encodingStrategy.domaineSize()==0)
			throw new CryptoException("Erreur C04: la key ne peut pas être ègale à 0 "
					+ "ou un multiple du domaineSize");
		this.shift = shift;
	}
	

	public int getShift() {
		return shift;
	}
	
	
}
