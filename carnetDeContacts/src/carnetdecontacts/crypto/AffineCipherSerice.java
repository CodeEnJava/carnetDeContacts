package carnetdecontacts.crypto;

public final class AffineCipherSerice implements Cipher{
	
	

	@Override
	public String encrypt(String message, Object... key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String decrypt(String message, Object... key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	/*
	 * Le principe du chiffrement affine modulo N
	 * 
	 * Ce chiffrement ne repose pas seulement sur une simple formule mathématique
	 * 
	 * Il va utiliser aussi deux concepts fondamentaux
	 * --> PGCD  (GCD)
	 * --> Relation (formule ) de Bézout
	 * 
	 * Ces deux notions garantissent une proriété essentielle en cryptographie
	 * 
	 * La BIJECTION:
	 * C'est-à-dire la capacité du système à chiffrer ou déchiffrer sans perte d'informations
	 * 
	 * Dans cette 3ème partie je propose d'aborder les points suivants:
	 * 
	 * Comment fonctionne le chiffrement affine modulo N
	 * 
	 * Pourquoi le PGCD (GCD) est indispensable dans sa mise en place
	 * Comment utiliser l'algorithme étendu d'EUCLIDE
	 * 
	 * Commencer une implémentation.
	 * 
	 * PRINCIPE DU CHIFFREMENT AFFINE MODULO N
	 * 
	 *    la formule : E(x) = (ax + b) mod N
	 *    
	 *    x: est la position d'une lettre dans l'alphabet
	 *    a: c'est le coefficient mutiplicateur
	 *    b: le décallage (offset)
	 *    N: taille de l'alphabet (pattern Strategy, la méthode domaineSize()	)
	 *    
	 *    Exemple : Lettres Majuscules A-Z  N= 26
	 *    
	 *    Chaque lettre correspond à un index
	 *    
	 *    A --> 0
	 *    B --> 1
	 *    .
	 *    .
	 *    Z --> 25
	 *    
	 *    On applique ensuite la fonction affine modulo N pour produire le texte chiffré
	 *    Soit E(x) = (5x+8) mod 26
	 *    
	 *    Message : HELLO
	 *    H --> 7 
	 *    E --> 4
	 *    L --> 11
	 *    L --> 11
	 *    O --> 14
	 *    
	 *    H-> 7  --> E(7) = (5*7+8) mod 26 = 43 mod 26 
	 *               43/26 = 1 reste = 43-26 = 17
	 *               E(7) = 17 --> R
	 *    On répéte cette action pour les autres lettres:
	 *    Message chiffré est le suivant: RCLLA
	 *    
	 *    L --> 11   E(11) = (5*11 +8) mod 26 = 63 mod 26
	 *               63/26 = 2 reste = 63-52 = 11
	 *               E(11) = 11 
	 *               
	 *    Comment déchiffrer le message?
	 *    
	 *    Pour déchiffrer il suffit d'utiliser l'inverse de la fonction affine
	 *    On doit résoudre l'équation suivante
	 *    pour noter l'inverse de a : (a^-1)
	 *    x = ((a^-1)(y-b))mod N
	 *    
	 *    Pour résoudre cette équation, il faut connaitre (a^-1)
	 *    
	 *    (a^-1) est l'inverse modulaire de a
	 *    
	 *    ET cet inverse modulaire n'existe pas toujours
	 *    
	 *    C'est là que le PGCD va intervenir.
	 *    
	 *    Pour que l'inverse modulaire existe, il faut vérifier la condition suivante:
	 *    
	 *    			pgcd(a,N) = 1
	 *    
	 *    Cela signifie que a et N sont premier entre eux
	 *    
	 *    SI cette condition est vérifiée:
	 *    
	 *    	La fonction affine est BIJECTIVE
	 *    	--> chaque lettre possède une image unique
	 *      --> on peut retrouver le message original sans perte d'information
	 *      
	 *    SI cette condition n'est pas respectée:
	 *    	Plusieurs lettres donnent le même résultat
	 *      LE DECHIFFREMENT EST IMPOSSIBLE
	 *      
	 *    
	 *    Utilisation de la formule de BEZOUT
	 *    
	 *    Pour calculer l'inverse modulaire, on utilise la relation de Bézout
	 *    
	 *    quelque soit (x et y) Appartenant à l'ensemble des entiers relatifs
	 *    
	 *    ax + Ny = pgcd(a,N)
	 *    
	 *    Sachant que pgcd(a,N) = 1
	 *    
	 *    ax + Ny = 1
	 *    
	 *    et le coefficient  x correspond à (a^-1 mod N)
	 *    cet inverse est calaculé à l'aide de l'algorithme d'Euclide étendu.
	 *    
	 *    
	 *    	Algorithme d'Euclide
	 *    	calcul du pgcd(a,b) --> utilisation des divisions succéssives
	 *      le principe :  a = q*b +r (q : le quotient et r le reste de la division Euclidienne
	 *      
	 *      Soit le pgcd(26,5) ?
	 *      
	 *      a = 26
	 *      b = 5
	 *      
	 *      q = a/b  --> q = 26/5 = 5  et le reste r = 1  --> 26 = 5*5 +1
	 *      on constate que le reste est différent de 0, on continue
	 *      Mise à jour de a et b
	 *      
	 *      a <- b  a = 5
	 *      b <- r  b = 1
	 *      
	 *      q = a/b  --> 5/1 = 5  r = 0 
	 *      
	 *      fin des divisions succéssives
	 *      
	 *      pgcd(26,5) = 1 
	 *      
	 *      On constate que 26 et 5 sont premiers entre eux
	 *      
	 *      ON souhaite obtenir les coéfficients de Bézout
	 *      
	 *      on part de la dernière équation pour lequel on a r différent de 0
	 *      
	 *      1 = 26 -5*5
	 *      on réécrit l'équation
	 *      
	 *      1 = (-5)*5 +(1)*26
	 *      
	 *      x = -5 et y = 1
	 *      
	 *      l'inverse modulaire : x = -5 mod 26
	 *      
	 *      -5 = 26*q +r
	 *      -5= 26(-1) +21
	 *      -5 mod 26 = 21
	 *      
	 *      donc l'inverse modulaire est 21
	 *      
	 *      vérification : 5*21 = 105
	 *                     105 mod 26 = 1       105/26 = 4
	 *                     
	 *                     Résultat pour le déchiffrement :
	 *                     
	 *                     D(x) = a^-1 (x - b)  mod 26
	 *                     D(x) = 21 (x-b) mod 26
	 *                     
	 *      Mettre en place une classe qui va me permettre d'obtenir
	 *      --> pgcd
	 *      --> les coéfficients de Bézout
	 *      --> l'inverse modulaire
	 *      
	 *      Mettre en place un algorithme non recursive
	 *      
	 *      Elle va permettre de calculer 
	 *      ax +by = pgcd(a,b)
	 *      
	 *      Le principe mis en oeuvre va permettre  de maintenir deux équations simultanément
	 *      
	 *      r(i) = S(i) * a + T(i) *b 
	 *      
	 *      r(i+1) = r(i-1) -q(i)*r(i) 
	 *      S(i+1) = S(1-1) -q(i)*S(i)
	 *      T(i+1) = T(1-1) -q(i)*T(i)
	 *      
	 *      Phase d'initialisation :
	 *      r(0) = a
	 *      r(1) = b
	 *      
	 *      S(0) = 1  T(0) = 0
	 *      S(1) = 0  T(1) = 1
	 *      
	 *      ENsuite on applique les divisions succéssives comme pour l'algorithme d'Euclide
	 *      
	 *      Exemple pgcd(5,26) = ?   5x +26y = pgcd(5,26)
	 *      
	 *      Phase d'initialisation :
	 *      r(0) = 5
	 *      r(1) = 26
	 *      
	 *      S(0) = 1  T(0) = 0
	 *      S(1) = 0  T(1) = 1  
	 *      
	 *       q <- r(0) /r(1)    q <- 5/26   q<-0
	 *       r(2) <- 5 - 0*26    r(2) <- 5
	 *       
	 *       Mise à jour de r(0) et r(1)
	 *       r(0) <- r(1)      r(0) <- 26
	 *       r(1) <- r(2)      r(1) <- 5
	 *       
	 *       On calcul les coéfficients de Bézout
	 *       
	 *       S(2) <- 1 - 0*0   S(2) <- 1
	 *       
	 *       Mise à jour de S(0) et S(1)
	 *       S(0) <-S(1)   S(0) = 0
	 *       S(1) <-S(2)   S(1) = 1
	 *       
	 *       T(2) = 0 - 0*1   T(2) <- 0
	 *       Mise à jour de T(0) et T(1)
	 *       T(0) <- T(1)   T(0) <- 1
	 *       T(1) <- T(2)   T(1) <- 0
	 *       
	 *       r(1) différent de 0 on recommence le même processus
	 *       
	 *       
	 *       q <- r(0) / r(1)  q <- 26/5   q <- 5
	 *       r(2) <- 26 -5*5 = 1
	 *       
	 *		 Mise à jour de r(0) et r(1)
	 *       r(0) <- r(1)      r(0) <- 5
	 *       r(1) <- r(2)      r(1) <- 1
	 *       
	 *       On calcul les coéfficients de Bézout             
	 *      
	 *       S(2) <- 0 - 5*1   S(2) <- -5
	 *       
	 *       Mise à jour de S(0) et S(1)
	 *       S(0) <-S(1)   S(0) = 1
	 *       S(1) <-S(2)   S(1) = -5
	 *       
	 *       T(2) = 1 - 5*0   T(2) <- 1 
	 *                         
	 *       Mise à jour de T(0) et T(1)
	 *       T(0) <- T(1)   T(0) <- 0
	 *       T(1) <- T(2)   T(1) <- 1      
	 *      
	 *       r(1) différent de 0 on recommence le même processus
	 *       
	 *       q <- r(0) / r(1)  q <- 5/1   q <- 5             
	 *       r(2) <- 5 -5*1 = 0
	 *      
	 *       Mise à jour de r(0) et r(1)
	 *       r(0) <- r(1)      r(0) <- 1
	 *       r(1) <- r(2)      r(1) <- 0
	 *      
	 *       S(2) <- 1 - 5*(-5)   S(2) <- 26
	 *       Mise à jour de S(0) et S(1)
	 *       S(0) <-S(1)   S(0) = -5
	 *       S(1) <-S(2)   S(1) = 26 
	 *       
	 *       T(2) = 0 - 5*1   T(2) <- -5
	 *       Mise à jour de T(0) et T(1)
	 *       T(0) <- T(1)   T(0) <- 1
	 *       T(1) <- T(2)   T(1) <- -5                        
	 *       
	 *       r(1) = 0 donc fin du processus
	 *       pgcd = r(0) = 1
	 *       x = S(0) =  -5
	 *       y = T(0) = 1
	 *      
	 *      
	         ∀  ≡
	 */
}
