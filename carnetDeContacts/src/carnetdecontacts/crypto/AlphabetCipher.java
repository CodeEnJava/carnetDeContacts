package carnetdecontacts.crypto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire contenant l’ensemble des alphabets utilisés
 * dans les différents algorithmes de chiffrement du projet.
 *
 * <p>Cette classe centralise :</p>
 * <ul>
 *     <li>Les lettres majuscules</li>
 *     <li>Les lettres minuscules</li>
 *     <li>Les caractères spéciaux</li>
 *     <li>Les chiffres</li>
 * </ul>
 *
 * <p>Elle est non instanciable car elle ne contient que des constantes.</p>
 *
 * @author Barois Stéphane
 * @version 1.0
 */
public final class AlphabetCipher {

	
	
	public static final String ALPHABET_AZ =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String ALPHABET_az =
			"abcdefghijklmnopqrstuvwxyz";
	
	public static final String ALPHABET_AZaz =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public static final String CUSTOM_ALPHABET = ALPHABET_AZaz+
			"éèàêïçù@#&'(§!?){}[]¬|°-_/*+=%$,;.<>`£ \n\\\"";
	
	public static final String NUMBER ="0123456789";
	
	public static final String CUSTOM_ALPHABET_NUMBER = CUSTOM_ALPHABET+NUMBER;
	
	/**
	 * Cette constante permet aux algorithmes de chiffrement de connaitre la taille du domaine 
	 * de l'alphabet personnalisé
	 * Elle sera utilisé par la méthode : domaineSize()
	 */
	public static final int LENGTH = CUSTOM_ALPHABET_NUMBER.length();
	
	
	/**
	 * Map pour associer chaque caractères de la chaine CUSTOM_ALPHABET_NUMBER à son index
	 * 
	 * Pourquoi ce choix?
	 * 
	 * Parce que les algorithmes de chiffrement travaillent mathématiquement sur les indicess et
	 * pas directement sur les caractères
	 * 
	 * Ce choix nous donne une structure de données avec un accès en temps constant O(1)
	 * C'est pour une optimisation importante
	 */
	
	
	public static final Map<Character,Integer> CHAR_INDEX_MAP;
	
	static {
		Map<Character,Integer> map = new HashMap<>();
		//remplir la map
		for(int i = 0; i<CUSTOM_ALPHABET_NUMBER.length();i++) {
			map.put(CUSTOM_ALPHABET_NUMBER.charAt(i), i);
		}
		/*
		 * initialiser CHARINDEXMAP en lecture seule
		 * Rendre immuable la MAP
		 * Cela va garantir que personne ne pourra modifier le domaine en cours d'exécution
		 * Cela va sécuriser le fonctionnement du cryptage
		 * 
		 * En cryptographie, la stabilité du domaine est essentielle.
		 * 
		 */
		
		CHAR_INDEX_MAP = Collections.unmodifiableMap(map);
	}
	
	//------------------------------------------------
	
	/*
	 * Cette partie va définir le domaine ASCII Imprimable non accentué
	 * la plage de caractère allant de 32 à 126 inclus
	 */
	public static final int ASCII_BEGIN = 32;
	public static final int ASCII_END = 126;
	public static final int ASCII_RANGE = 95;
	
	public static final Map<Character,Integer> CHAR_INDEX_MAP_ASCII;
	static {
		Map<Character,Integer> map = new HashMap<>();
		//remplir la map
		for(int i = ASCII_BEGIN; i<=ASCII_END;i++) {
			map.put((char)i, i-ASCII_BEGIN);
		}
		/* initialiser CHAR_INDEX_MAP_ASCII en lecture seule
		 * Rendre immuable la MAP
		 * Cela va garantir que personne ne pourra modifier le domaine en cours d'exécution
		 * Cela va sécuriser le fonctionnement du cryptage
		 */
		CHAR_INDEX_MAP_ASCII = Collections.unmodifiableMap(map);
	}
	
	/*
	 * Cette constante va définir la taille du domaine Unicode
	 * il y a 65536 caractères possible, unicode est codé avec un format de 16bits
	 * Le type char en java est codé en 16bits non signé
	 * 
	 * On va travaillé sur un domaine complets et sans  restriction d'alphabet
	 * 	
	 */
	public static final int UNICODE_RANGE = 65536;
	
	/**
	 * Constructeur privé empêchant l’instanciation.
	 * Cette classe est purement UTILITAIRE
	 * C'est une bonne pratique en Java pour les classes contenant uniquement des constantes
	 */
	private AlphabetCipher() {
		super();
	}
	/*
	 * Cette classe respecte plusieurs principes:
	 * 
	 *  -> Single Responsability
	 *     Elle ne fait qu'une seule chose: Définir l'alphabet (le domaine utilisé pour le chiffrement)
	 *    
	 *  -> Immutabilité
	 *     Les strutures sont sécurisées
	 *     
	 *  -> Performance
	 *     Mie en oeuvre de HasMap pour avoir des accès rapide ( en temps constant O(1))
	 *     
	 *  -> Cohérence
	 *     Tous les algorithmes utilisent le même domaine
	 *     
	 *     
	 *   Position de cette classe dans l'architecture global
	 *   
	 *   AlphabetCiper  --> EncodingStrategy --> xxxCipherService
	 *   
	 *   La classe AlphabetCipher fournit les données
	 *   EncodingStrategy définit comment utiliser ces données
	 *   xxxCipherService applique l'algorithme de chiffrement
	 *   
	 *   Une bonne architecture:
	 *    --> Séparer les responsabilité
	 *    --> Centraliser les constantes
	 *    --> Protège les strutures critiques
	 *    --> Facilité pour faire évoluer le code
	 *    
	 */
	
	
}
