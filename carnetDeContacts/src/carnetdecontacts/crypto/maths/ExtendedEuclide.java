package carnetdecontacts.crypto.maths;

import carnetdecontacts.crypto.CryptoException;

public final class ExtendedEuclide {

	
	private final int gcd;
	private final int x;
	private final int y;
	private final int modulus;
	
	public ExtendedEuclide(int a, int b) {
		if(b==0)
			throw new CryptoException("Le modulo ne peut pas être 0.");
		this.modulus = b;
		int[] coef = compute(a,b);
		
		gcd = coef[0];
		x = coef[1];
		y= coef[2];
	}

	private int[] compute(int a, int b) {
		int r0 = a;
		int r1 = b;
		
		//      S(0) = 1  T(0) = 0
		//      S(1) = 0  T(1) = 1
		
		int S0 = 1;
		int S1 = 0;
		
		int T0 = 0;
		int T1 = 1;
		
		while(r1!=0) {
			int q = r0/r1;
			int r2 = r0 -q*r1;
			
			//       Mise à jour de r(0) et r(1)
			//       r(0) <- r(1)     
			//       r(1) <- r(2) 
			
			r0 = r1;
			r1 = r2;
			
			//       On calcul les coéfficients de Bézout
			
			int S2 = S0 - q*S1;
			//       Mise à jour de S(0) et S(1)
			//       S(0) <-S(1)   
			//       S(1) <-S(2) 
			
			S0 = S1;
			S1 = S2;
			
			int T2 = T0 - q*T1;
			//       Mise à jour de T(0) et T(1)
			//       T(0) <-T(1)   
			//       T(1) <-T(2) 
			
			T0 = T1;
			T1 = T2;
		}
		
		return new int[] {r0,S0,T0};
	}
	
	public int modInverse() {
		if(!hasModInverse())
			throw new CryptoException("Pas d'inverse modulaire");
		return Math.floorMod(x, modulus);
	}
	
	public boolean hasModInverse() {
		return this.gcd == 1;
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
	public static boolean hasInveseModulaire(int a, int b) {
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
