package carnetdecontacts.crypto.maths;

import carnetdecontacts.crypto.CryptoException;

public final class ExtendedEuclide {

	
	private final int gcd;
	private final int x;
	private final int y;
	private final int modulus;
	
	//modif
	private final int coefMultiplicateur;
	
	public ExtendedEuclide(int a, int b) {
		
		if(a == 0)
		    throw new CryptoException("0 n'a pas d'inverse modulaire.");
		
		if(b == 0)
			throw new CryptoException("Le modulo ne peut pas être 0.");
		
		this.modulus = Math.abs(b);
		
		//modif
		this.coefMultiplicateur = a;
		
		long[] coef = compute(a, this.modulus);
		
		
		gcd = (int) coef[0];
		x = (int) Math.floorMod(coef[1], modulus);//pas d’overflow logique
		y= (int) coef[2];
		
	}

	private long[] compute(long a, long b) {
		long r0 = a;
		long r1 = b;
		
		//      S(0) = 1  T(0) = 0
		//      S(1) = 0  T(1) = 1
		
		long S0 = 1;
		long S1 = 0;
		
		long T0 = 0;
		long T1 = 1;
		
		while(r1!=0) {
			long q = r0/r1;
			long r2 = r0 -q*r1;
			
			//       Mise à jour de r(0) et r(1)
			//       r(0) <- r(1)     
			//       r(1) <- r(2) 
			
			r0 = r1;
			r1 = r2;
			
			//       On calcul les coéfficients de Bézout
			
			long S2 = S0 - q*S1;
			//       Mise à jour de S(0) et S(1)
			//       S(0) <-S(1)   
			//       S(1) <-S(2) 
			
			S0 = S1;
			S1 = S2;
			
			long T2 = T0 - q*T1;
			//       Mise à jour de T(0) et T(1)
			//       T(0) <-T(1)   
			//       T(1) <-T(2) 
			
			T0 = T1;
			T1 = T2;
		}
		
		
		return new long[] {r0,S0,T0};
	}
	
	public int modInverse() {
		if(!hasModInverse())
			throw new CryptoException("Pas d'inverse modulaire");
		return x;
	}
	
	public boolean hasModInverse() {
		// modif
		return Math.abs(this.gcd) == 1 && this.coefMultiplicateur%modulus !=0;
	}

	public int getGcd() {
		return gcd;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public  static int modInverse(int a, int n) {
		return new ExtendedEuclide(a, n).modInverse();
	}
	
	
	/* AJOUT */
	public static boolean hasInverseModulaire(int a, int b) {
		return new ExtendedEuclide(a, b).hasModInverse();
	}
	
	public static int gcd(int a, int b) {
		while(b!=0) {
			int tempo = b;
			b = a %b;
			a = tempo;
		}
		
		return Math.abs(a);
	}
	
}
