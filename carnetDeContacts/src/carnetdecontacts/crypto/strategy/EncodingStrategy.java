package carnetdecontacts.crypto.strategy;
/**
 * Cette interface représente la strategy
 * 
 * On va réaliser des classes concrètes par exemple
 * 
 * 		AsciiImprimableEncodingStrategy    ( utilise un alphabet dont le domaine est 32-126 soit une longueur de 95 caractères
 * 		CustomAlphabetEncodingStrategy     (Utilise un alphabet personnalisé,,,,,,)
 * 		UnicodeEncodingStrategy            (utilise tous les caractères Unicode 65536 caractères possibles)
 * 
 * 		Le chiffrement ArithmeticSerieCipherService va représenter le contexte
 * 
 * 	
 * 		Le service de chiffrement reçoit une strategy via son constructeur
 * 		il ne sait pas quel type d'encodage est utilisé
 * 
 * 		il délègue simplement:
 * 				La normalisation
 * 				La taille du domaine
 * 				Conversion char/index
 * 				la sécurité
 * 
 * 		Ce principe respecte l'inversion de dépendance
 * 		
 * 		Cette architecture respecte plusieurs principes SOLID
 * 		
 *      -> Single Responsibility:
 *      	Le chiffrement c'est de chiffré , L'encodage gère le domaine
 *      	
 *         Open/Closed
 *         On peut ajouter une nouvelle stratégie sans modifier le service
 *         
 *         Dependency Inversion
 *         Le service dépend d'une abstraction et PAS D'UNE CLASSE CONCRETE
 * 
 */
public interface EncodingStrategy {
	/**
	 * Cette méthode permet de ramener une valeur dands le domaine valide
	 * 
	 * Exemple :
	 *  Dans un alphabet A-Z qui contient 26 lettres, on appliquer un module 26
	 * @param value
	 * @return
	 */
	int normalize(long value);
	
	/**
	 * Cette méthode retourne la taille du domaine
	 * C'est cette valeur qui permet au chiffrement de faire les calculs mathématiques
	 * @return
	 */
	int domaineSize();
	
	/**
	 * Cette méthode permet de réaliser la conversion entre un caractère et son index
	 * Le chiffrement travaille sur des nombre et pas directement sur des caractères
	 * @param c
	 * @return
	 */
	int toIndex(char c);
	
	/**
	 * Cette méthode permet de réaliser la conversion entre un caractère et son index
	 * Le chiffrement travaille sur des nombre et pas directement sur des caractères
	 * @param c
	 * @return
	 */
	char toChar(long index);
	
	/**
	 * Cette méthode va garantir que le caractère analysé respecte le domaine défini
	 * Objectif renforcer la robustesse et la sécurité du module
	 * @param c
	 * @return
	 */
	boolean isCharValid(char c);
	
	/**
	 * Cette méthode va garantir que l'ensemble des caractères contenu dans le message  respecte le domaine défini
	 * Objectif renforcer la robustesse et la sécurité du module
	 * @param c
	 * @return
	 */
	boolean isMessageValid(String message);

}
