package carnetdecontacts.crypto_old;

import java.security.SecureRandom;

import carnetdecontacts.crypto.CryptoException;
import carnetdecontacts.crypto.maths.ExtendedEuclide;
import carnetdecontacts.crypto.strategy.EncodingStrategy;
import carnetdecontacts.crypto.strategy.UnicodeEncoding;
import fr.bs.dev.input.Lecture;

public final class AffineKeys_old {
	
/* ∀  ≡ */	
	
/* la formule pour chiffrer: E(x) = (ax + b) mod N */
	
	private final int a;
	private final int b;
	
/* 	la formule pour déchiffrer : D(x) = ((a^-1)( y -b)) mod N  */
	
	private final int inverseModulaire;

    private final EncodingStrategy encodingStrategy;
    
    private int minOrder ;
    private static int K = 4; 

	public AffineKeys_old(int a, int b,int k, EncodingStrategy encodingStrategy) {
		
		if(k==0 )k = K;
		ExtendedEuclide euclide = new ExtendedEuclide(a, b);
		if(!euclide.hasModInverse())
			throw new CryptoException("Erreur AK01: Les élément de la clé ne sont pas valide, car l'inverse modulaire n'existe pas.");
		
		/*
		 * Existe t-il un couple (a,b)  tel que message = encrypt(message) ?
		 * 
		 * Equation : E(x) = (ax+b) mod N
		 * 
		 * On recherche s'il existe un couple (a,b) tel que E(x) = x et ∀x
		 * 
		 * (ax +b) mod N = x   --> ax +b -x ≡ 0 mod N   (a-1)x + b ≡ 0 mod N
		 * 
		 * Pour que tous les caractères du message restent , il faut que:
		 * (a-1) ≡ 0 mod N  --> a ≡ 1 mod N
		 *  b ≡ 0 mod N 
		 *  
		 *  donc le couple (1,0) vérifie l'équation E(x) = x et  ∀x  qui appartient au domaine Unicode
		 *  
		 * 
		 */
		
		
		if(encodingStrategy instanceof UnicodeEncoding && a==1 && b==0)
			throw new CryptoException("Erreur AK02: Pour l'encodage unicode, le couple (1,0) n'est pas accepté.");
		
		
		/*
		 * Existe-t-il un couple (a,b) qui donnent des caractères inchangés ou trop de caractères inchangés.
		 * 
		 * Pas pour tous les caractères mais pour un message donné, il peut exister:
		 *  Si (a-1)x + b ≡ 0 mod N Pour des caractères spécifiques x présent dans le message
		 *  
		 *  Dans certain cas des caractères peuvent rester FIXE mais pas pour tout l'alphabet du domaine
		 *  
		 *  On peut mettre en place une méthode pour détecter automatiquement les clés 'faible', qui FIXE trop de caractères
		 *  OU analyser si certaine clés créent des cycles courts.
		 *  
		 *  1- Comment détecter si une clé est faible?
		 *  
		 *  ON va dire une clé est faible si:
		 *  	-> elle laisse trop de caractères inchangés (Points FIXES)
		 *  	-> elle crée des cycle courts (répétition rapdide)
		 *  
		 *  Un point fixe vérifie l'équation suivante:(a-1)x + b ≡ 0 mod N
		 *  il faut résoudre cette équation :  (a-1)x ≡ -b mod N
		 *  
		 *  si le pgcd(a-1,N) = d, la valeur d, représente ne nombre de points fixes ( le nombre de caractères qui restent inchangés)
		 *  
		 *  Exemple : Un domaine de 26 lettres  A-Z  N = 26
		 *  a = 5 et b = 8   E(x) = (5x +8) mod 26
		 *  
		 *   (a-1)x ≡ -b mod N  --> 4x ≡ -8 mod 26  --> 4x ≡ 18 mod 26
		 *   pgcd(4,26)  = ?       26 = 13 x 2  et 4 = 2 x 2    donc le pgcd(4,26) = 2
		 *   
		 *   Il existe deux caractères du domaine A-Z qui restent inchangés
		 *   
		 *   Verifications :
		 *   Lettres 		A	B	C	D	E	F	G	H	I	J 	K	L	M	N	O	P	Q	R	S	T	U	V	W	X	Y	Z
		 *   index			0	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	16	17	18	19	20	21	22	23	24	25
		 *   
		 *   E(x)           8	13	18	23	2	7	12	17	22	1	6	11	16	21	0	5	10	15	20	25	4	9	14	19	24	3
		 *   
		 *   il existe deux lettres (L,Y) qui restent inchangés, donc clé faibles
		 *  
		 */
		
		if(countFixePoint(a, b, encodingStrategy.domaineSize())>1)
			throw new CryptoException("Erreur AK03: Cette clé est trop faible car il y a trop de caractères inchangés.");
		
		/*
		 * Analyse des cycles
		 * Le chiffrement affine est une permutation de caractères
		 * La permutation peut être vu comme un ensemble de cycles
		 * 
		 * Exemple :
		 *  A -> D -> G -> A   un cycle de longueur 3
		 *  B -> B   Point FIXE
		 *  
		 *  Un cycle court => faible sécurité
		 *  un cycle long => il y a une meilleur diffusion
		 *  
		 *  Mettre en oeuvre un lien entre la structure des cycles et l'ordre multiplicatif
		 *  
		 *  E(x) = (ax+b) mod N
		 *  
		 *  transformer cette fonction pour éliminer le terme b
		 *  
		 *  On cherche un point fixe x0  tel que E(x0) = x0
		 *  
		 *  ax0 + b = x0 mod N
		 *  (a-1)x0 ≡ -b mod N
		 *  
		 *  L'inverse modulaire existe à ce niveau du traitement
		 *  
		 *  x0 = (-b/(a-1)) mod N, il existe un point fixe
		 *  
		 *  y = x -x0   x = y + x0
		 *  
		 *  Transformation
		 *  
		 *  E(x) = ax + b 
		 *  E(y +x0) = a(y+x0) + b = ay + ax0+b   mais on a ax0 +b = x0
		 *  
		 *  donc E(x) = ay + x0
		 *  
		 *  Résultat  : y -> ay mod N
		 *  
		 *  Transformer le chiffrement affine en multiplication
		 *  
		 *  Il faut étudier le comportement
		 *  
		 *  y -> ay mod n
		 *  
		 *  y1 = a y
		 *  y2 = (a^2) y
		 *  y3 = (a^3)y
		 *  
		 *  condition pour revenir au point de départ (cycle)
		 *  (a^k)y ≡ y mod N
		 *  
		 *  si y!=0 on obtient  a^k ≡ 1 mod N
		 *  
		 *  On cherche le plus petit élément k tel que a^k ≡ 1 mod N, ceci va représenter l'ordre multiplicatif, cette valeur va donner
		 *  la longueur du cycle.
		 *  
		 *  
		 *  Exemple N = 7 et a = b
		 *  
		 *   3^1 ≡ 3 mod 7
		 *   9 ≡ 2 mod 7
		 *   27 ≡ 6 mod 7
		 *   81 ≡ 4 mod 7
		 *   243 ≡ 5 mod 7
		 *   729 ≡ 1 mod 7
		 *   
		 *   Résultat l'ordre et de 6
		 *   pour ce domaine on a une bonne diffusion
		 *   
		
		 */
		
		minOrder = encodingStrategy.domaineSize()/k;
		
		if(multiplicativeOrder(a, encodingStrategy.domaineSize())<minOrder)
			throw new CryptoException(String.format("Erreur AK04: La clé présente un cycle inférieur à %d, veuillez en choisir une autre.",minOrder));
		
		
		this.a = a;
		this.b = b;
		this.encodingStrategy = encodingStrategy;
		this.inverseModulaire = euclide.modInverse();
	}
	
	
	
	public int getA() {
		return a;
	}


	public int getB() {
		return b;
	}

	public int getInverseModulaire() {
		return inverseModulaire;
	}

	public EncodingStrategy getEncodingStrategy() {
		return encodingStrategy;
	}


	private static int multiplicativeOrder(int a, int N) {
		ExtendedEuclide euclide = new ExtendedEuclide(a, N);
	    if(!euclide.hasModInverse())
	    	return -1;
	    // mise en place de la recherche de l'ordre.
	    int limite = N; // Pour éviter potentiellement boucle infinie 
	    int result = 1;
	    int power = a%N;
	    
	    while(power!=1 && result < limite) {
	    	power = (power*a)%N;
	    	result++;
	    }
	    return result;
	}
	
	private static int countFixePoint(int a, int b, int N) {
		
		int d = ExtendedEuclide.gcd(a-1, N);
		
		// on vérifie qu d divise b
		//System.out.println(String.format("%d modulo %d = %d", b,d,(b%d)));
		if(b%d !=0)
			return 0;
		
		return d;
	
	}
    
    
	/* Mise en place d'un générateur de key aléatoire */
	
	public static int[] keyRandomGenerator(int N) {
	System.out.println("size :"+N);
	/*
	 * N = 104   --> K = 4   -> minOrder = 104/4 = 26
	 *               K = 5                 104/5 = 20
	 *               k = 6                 104/6 = 17
	 *                                     104/7 = 14
	 *                                     104/8 = 13
	 *                                     104/9 = 11
	 */
	SecureRandom random = new SecureRandom();
	int k = K;
	int a = 1;
	int order = 0;
	
	int minOrder = N/k;
	
	
	int [] used = new int[N+1];
	
	used[1] = 1;
	
	/* Recherche de l'ordre multiplicatif */
	while(order<minOrder) {
		
		a = 1 +random.nextInt(N); /* une valeur comprise entre 1 et N+1*/
		
		if(used[a]==0) {
			used[a]=a;
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
	
	
	while(fixe>1 || !ExtendedEuclide.hasInverseModulaire(a, b)) {
		b = 1 +random.nextInt(N); /* une valeur comprise entre 1 et N*/
		
		if(used[b]==0) {
			used[b]=b;
			fixe = countFixePoint(a, b, N);
		}
	
	}

	return new int[] {a,b,k};
   }

	private static boolean allVisited(int[] array) {
		for(int i = 1;i<array.length;i++) {
			if(array[i] == 0){
				return false;
			}
		}
		return true;
	}

	
}
