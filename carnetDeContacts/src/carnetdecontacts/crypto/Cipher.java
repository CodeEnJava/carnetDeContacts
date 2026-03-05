package carnetdecontacts.crypto;
/**
 * Interface définissant le contrat commun
 * à tous les algorithmes de chiffrement du projet.
 *
 * <p>Chaque implémentation doit garantir :</p>
 * <ul>
 *     <li>Un chiffrement déterministe</li>
 *     <li>Un déchiffrement réversible</li>
 *     <li>Une gestion explicite des erreurs via {@link CryptoException}</li>
 * </ul>
 *
 * <p>Le paramètre {@code key} est variadique afin de permettre :</p>
 * <ul>
 *     <li>Une clé simple (ex : César)</li>
 *     <li>Plusieurs paramètres (ex : chiffrement avancé)</li>
 *     <li>Une absence de clé si l’algorithme n’en nécessite pas</li>
 * </ul>
 *
 * <p>Chaque implémentation est responsable de :</p>
 * <ul>
 *     <li>Valider le nombre de paramètres reçus</li>
 *     <li>Valider leur format</li>
 * </ul>
 */
public interface Cipher {

	/**
     * Chiffre un message.
     *
     * @param message message à chiffrer (non null)
     * @param key paramètres de clé nécessaires à l’algorithme
     * @return message chiffré
     * @throws CryptoException en cas de paramètre invalide
     */
	
	/*
	 * Modification utilisation d'un tableau d'objets, pour permettre l'utilisation de
	 * ArithmeticKey
	 */
	public String encrypt(String message,Object ...key);
	
	 /**
     * Déchiffre un message.
     *
     * @param message message chiffré (non null)
     * @param key paramètres de clé nécessaires à l’algorithme
     * @return message original
     * @throws CryptoException en cas de paramètre invalide
     */
	
	/*
	 * Modification utilisation d'un tableau d'objets, pour permettre l'utilisation de
	 * ArithmeticKey
	 */
	public String decrypt(String message,Object ...key);
}
