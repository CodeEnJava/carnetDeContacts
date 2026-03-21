package carnetdecontacts.crypto.keys;

import java.security.SecureRandom;

import carnetdecontacts.crypto.CryptoException;
import carnetdecontacts.crypto.maths.ExtendedEuclide;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;
/**
 * Représente une paire de clés pour le chiffrement affine.
 *
 * <p>
 * Le chiffrement affine repose sur la transformation suivante :
 * </p>
 * <pre>
 *     E(x) = (a * x + b) mod N
 * </pre>
 *
 * <p>
 * et son inverse :
 * </p>
 * <pre>
 *     D(x) = (a⁻¹ * (y - b)) mod N
 * </pre>
 *
 * <p>
 * où :
 * <ul>
 *     <li>{@code a} est la clé multiplicative</li>
 *     <li>{@code b} est la clé additive</li>
 *     <li>{@code N} est la taille du domaine d'encodage</li>
 * </ul>
 *
 * <h2>Contraintes de validité</h2>
 * <ul>
 *     <li>{@code a} doit être inversible modulo {@code N} (pgcd(a, N) = 1)</li>
 *     <li>Le couple (1,0) est interdit pour certains encodages (ex : Unicode)</li>
 *     <li>Le nombre de points fixes doit être limité</li>
 *     <li>L'ordre multiplicatif doit être suffisamment élevé pour éviter les cycles courts</li>
 * </ul>
 *
 * <h2>Sécurité</h2>
 * <p>
 * Cette classe implémente plusieurs mécanismes de protection contre les clés faibles :
 * </p>
 * <ul>
 *     <li>Détection des points fixes : caractères inchangés après chiffrement</li>
 *     <li>Analyse des cycles : éviter les cycles courts dans la permutation</li>
 *     <li>Validation mathématique via l'algorithme d'Euclide étendu</li>
 * </ul>
 *
 * <p>
 * Une clé est considérée comme faible si :
 * </p>
 * <ul>
 *     <li>Elle produit trop de points fixes</li>
 *     <li>Elle induit des cycles de petite taille</li>
 * </ul>
 *
 * @author BAROIS Stéphane
 * @version 1.0
 */
public final class AffineKeys {
		/**
	     * Clé multiplicative du chiffrement affine.
	     */
		private final int a;
		
		 /**
	     * Clé additive du chiffrement affine.
	     */
		private final int b;
		
		/**
	     * Inverse modulaire de {@code a} modulo N.
	     */
		private final int inverseModulaire;

		 /**
	     * Stratégie d'encodage utilisée (définit le domaine).
	     */
	    private final EncodingStrategy encodingStrategy;
	    
	    /**
	     * Ordre minimal accepté pour éviter les cycles courts.
	     */
	    private int minOrder ;
	    
	    /**
	     * Paramètre de sécurité utilisé pour ajuster l'ordre minimal.
	     */
	    private static int K = 4; 

	    
	    /**
	     * Construit une paire de clés affine après validation complète.
	     *
	     * @param a clé multiplicative
	     * @param b clé additive
	     * @param k facteur de sécurité influençant l'ordre minimal
	     * @param encodingStrategy stratégie d'encodage utilisée
	     *
	     * @throws CryptoException si :
	     * <ul>
	     *     <li>l'inverse modulaire de {@code a} n'existe pas</li>
	     *     <li>le couple (a,b) est trivial (ex : (1,0) en Unicode)</li>
	     *     <li>la clé génère trop de points fixes</li>
	     *     <li>l'ordre multiplicatif est insuffisant</li>
	     * </ul>
	     */
		public AffineKeys(int a, int b,int k, EncodingStrategy encodingStrategy) {
			
			if(k==0 )k = K;
			
			if(!ExtendedEuclide.hasInverseModulaire(a, encodingStrategy.domaineSize()))
				throw new CryptoException("Erreur AK01: Les élément de la clé ne sont pas valide, car l'inverse modulaire n'existe pas.");
			
			if(encodingStrategy instanceof UnicodeEncoding && a==1 && b==0)
				throw new CryptoException("Erreur AK02: Pour l'encodage unicode, le couple (1,0) n'est pas accepté.");
			

			if(countFixePoint(a, b, encodingStrategy.domaineSize())>1)
				throw new CryptoException("Erreur AK03: Cette clé est trop faible car il y a trop de caractères inchangés.");
			
			
			minOrder = encodingStrategy.domaineSize()/k;
			
			if(multiplicativeOrder(a, encodingStrategy.domaineSize())<minOrder)
				throw new CryptoException(String.format("Erreur AK04: La clé présente un cycle inférieur à %d, veuillez en choisir une autre.",minOrder));
			
			this.a = a;
			this.b = b;
			this.encodingStrategy = encodingStrategy;
			this.inverseModulaire = ExtendedEuclide.modInverse(a, encodingStrategy.domaineSize());
		}
		
		
		 /**
	     * Retourne la clé multiplicative.
	     *
	     * @return la valeur de {@code a}
	     */
		public int getA() {
			return a;
		}

		/**
	     * Retourne la clé additive.
	     *
	     * @return la valeur de {@code b}
	     */
		public int getB() {
			return b;
		}

		/**
	     * Retourne l'inverse modulaire de {@code a}.
	     *
	     * @return {@code a⁻¹ mod N}
	     */
		public int getInverseModulaire() {
			return inverseModulaire;
		}

		 /**
	     * Retourne la stratégie d'encodage associée.
	     *
	     * @return la stratégie d'encodage
	     */
		public EncodingStrategy getEncodingStrategy() {
			return encodingStrategy;
		}


		/**
	     * Calcule l'ordre multiplicatif de {@code a} modulo {@code N}.
	     *
	     * <p>
	     * L'ordre multiplicatif est le plus petit entier {@code k} tel que :
	     * </p>
	     * <pre>
	     *     a^k ≡ 1 mod N
	     * </pre>
	     *
	     * <p>
	     * Cet indicateur est utilisé pour analyser la longueur des cycles
	     * dans la permutation induite par le chiffrement affine.
	     * </p>
	     *
	     * @param a valeur à analyser
	     * @param N modulo
	     * @return l'ordre multiplicatif, ou -1 si l'inverse n'existe pas
	     */
		private static int multiplicativeOrder(int a, int N) {
			//ExtendedEuclide euclide = new ExtendedEuclide(a, N);
			
		    //if(!euclide.hasModInverse())
		    //	return -1;
			
			// modif
			if(!ExtendedEuclide.hasInverseModulaire(a, N))
				return -1;
	
		    int limite = N; // Pour éviter potentiellement boucle infinie 
		    int result = 1;
		    int power = a%N;
		    
		    while(power!=1 && result < limite) {
		    	power = (power*a)%N;
		    	result++;
		    }
		    return result;
		}
		
		
		 /**
	     * Calcule le nombre de points fixes du chiffrement affine.
	     *
	     * <p>
	     * Un point fixe est une valeur {@code x} telle que :
	     * </p>
	     * <pre>
	     *     (a - 1)x + b ≡ 0 mod N
	     * </pre>
	     *
	     * <p>
	     * Le nombre de solutions dépend du :
	     * </p>
	     * <pre>
	     *     d = pgcd(a - 1, N)
	     * </pre>
	     *
	     * <p>
	     * Si {@code d} divise {@code b}, alors il existe {@code d} points fixes.
	     * </p>
	     *
	     * @param a clé multiplicative
	     * @param b clé additive
	     * @param N taille du domaine
	     * @return nombre de points fixes
	     */
		private static int countFixePoint(int a, int b, int N) {
			
			int d = ExtendedEuclide.gcd(a-1, N);
			// modif si b<0
			if(Math.floorMod(b, d) != 0)
				return 0;
			
			return d;
		
		}
	    
	    
		
		 /**
	     * Génère aléatoirement une clé affine valide.
	     *
	     * <p>
	     * Le processus garantit :
	     * </p>
	     * <ul>
	     *     <li>un ordre multiplicatif suffisant</li>
	     *     <li>un nombre limité de points fixes</li>
	     *     <li>l'existence d'un inverse modulaire</li>
	     * </ul>
	     *
	     * @param N taille du domaine
	     * @return un tableau contenant {@code [a, b, k]}
	     */
		public static int[] keyRandomGenerator(int N) {
		
		SecureRandom random = new SecureRandom();
		int k = K;
		int a = 1;
		int order = 0;
		
		int minOrder = N/k;
		
		
		int [] used = new int[N+1];
		
		used[1] = 1;
		
		/* Recherche de l'ordre multiplicatif */
		while(order<minOrder) {
			
			a = 1 +random.nextInt(N); /* une valeur comprise entre 1 et N*/
			
			if(used[a]==0) {
				used[a]=a;
				//optimisation ne calcul que si il y a un inverse modulaire
				if (ExtendedEuclide.gcd(a, N) == 1) 
					order = multiplicativeOrder(a, N);
			}
			
			/*
			 * Si après avoir testé toute les combinaisons pour la variable a,
			 * On recommence mais après avoir mis à jour k
			 * 
			 */
			
			if(allVisited(used)) {
				used = new int[N+1];
				k++;
				k = Math.min(k, 16);
				minOrder = N/k;
			}
		}
		
		
		/*
		 * Recherche de la valeur de b avec les points fixes <=1
		 */
		
		int fixe = N;
		int b =1;
		used = new int[N+1];
		used[1] = 1;
		
		
		while(fixe>1) {
			b = 1+random.nextInt(N); /* une valeur comprise entre 1 et N*/
			
			if(used[b]==0) {
				used[b]=b;
				fixe = countFixePoint(a, b, N);
			}
		
		}

		return new int[] {a,b,k};
	   }

		 /**
	     * Vérifie si toutes les valeurs d'un tableau ont été visitées.
	     *
	     * @param array tableau d'état
	     * @return {@code true} si toutes les cases sont non nulles
	     */
		private static boolean allVisited(int[] array) {
			for(int i = 1;i<array.length;i++) {
				if(array[i] == 0){
					return false;
				}
			}
			return true;
		}

		
}
