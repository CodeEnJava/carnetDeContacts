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

	public static final String ALPHABETMAJMIN =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public static final String ALPHABETMAJ =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String ALPHABET = ALPHABETMAJMIN+
			"éèàêïçù@#&'(§!?)}°-_/*+%$,;.<>`£ \n";
	
	public static final String NUMBER ="0123456789";
	
	public static final String ALPHABETNUMBER = ALPHABET+NUMBER;
	public static final int LENGTH = ALPHABETNUMBER.length();
	
	
	/**
	 * Map pour associer chaque caractères de la chaine ALPHABETNUMBER à son index
	 */
	
	
	public static final Map<Character,Integer> CHARINDEXMAP;
	
	static {
		Map<Character,Integer> map = new HashMap<>();
		//remplir la map
		for(int i = 0; i<ALPHABETNUMBER.length();i++) {
			map.put(ALPHABETNUMBER.charAt(i), i);
		}
		// initialiser CHARINDEXMAP en lecture seule
		// pour garantir son contenu et le fonctionnement du cryptage
		CHARINDEXMAP = Collections.unmodifiableMap(map);
	}
	
	
	
	/**
	 * Constructeur privé empêchant l’instanciation.
	 */
	private AlphabetCipher() {
		super();
	}
	
	
}
