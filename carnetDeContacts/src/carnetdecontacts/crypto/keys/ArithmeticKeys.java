package carnetdecontacts.crypto.keys;

import carnetdecontacts.crypto.CryptoException;

public final class ArithmeticKeys {

	private long a = 0l;
	private long d = 0l;
	
	/*
	 * Cette classe va permettre d'obtenir le couple (a,d) immuable et valide pour le chiffrage
	 */
	public ArithmeticKeys(int a, int d, int domaineSize) {
		
		if( a== 0 && d == 0) {
			throw new CryptoException(
					"Clé arithmétique invalide : les paramètres a et d ne peuvent pas être tous les deux égaux à 0.\n"
					+ "Valeurs reçues : a=" + a + ", d=" + d + ".\n"
					+ "Rappel : dans le chiffrement affine, au moins un des paramètres doit être non nul."
					);
		}
			
		if( Math.floorMod(a, domaineSize)== 0 && d ==0) {
			throw new CryptoException(
					"Clé arithmétique invalide : la transformation serait constante.\n"
					+ "Condition détectée : a mod N = 0 et d = 0.\n"
					+ "Valeurs reçues : a=" + a + ", d=" + d + ", N=" + domaineSize + ".\n"
					+ "Rappel : pour garantir une transformation correcte, il faut que :\n"
					+ " -> a mod N ≠ 0\n"
					+ " -> ou d ≠ 0"
					);
		}
		
		this.a = (long)a;
		this.d = (long)d;
	}

	public long getA() {
		return a;
	}

	public long getD() {
		return d;
	}
	
	
}
