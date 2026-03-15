package carnetdecontacts.democrypto;

import carnetdecontacts.crypto.maths.ExtendedEuclide;

public class TestExtendedEuclide {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //Exemple pgcd(5,26) = ?   5x +26y = pgcd(5,26)
		// pgcd(5,26) = 1
		// x = -5 et y = 1
		int a = 5;
		int n = 26;
		ExtendedEuclide euclide = new ExtendedEuclide(a, n);
		
		System.out.println("pgcd = "+euclide.getGcd());
		System.out.println("x = "+euclide.getX());
		System.out.println("y = "+euclide.getY());
		
		// Fonctionne
	}

}
