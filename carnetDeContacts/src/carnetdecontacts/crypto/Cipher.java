package carnetdecontacts.crypto;
/**
 * Interface définissant le contrat commun
 * à tous les algorithmes de chiffrement du projet.
 *
 * <p>Toute implémentation doit fournir :</p>
 * <ul>
 *     <li>Une méthode de chiffrement</li>
 *     <li>Une méthode de déchiffrement</li>
 * </ul>
 *
 * <p>Le paramètre {@code key} représente la clé utilisée
 * par l’algorithme (numérique ou textuelle selon l’implémentation).</p>
 */
public interface Cipher {

	/**
     * Chiffre un message.
     *
     * @param message message à chiffrer
     * @param key clé de chiffrement
     * @return message chiffré
     */
	public String encrypt(String message,String key);
	
	/**
     * Déchiffre un message.
     *
     * @param message message chiffré
     * @param key clé de déchiffrement
     * @return message déchiffré
     */
	public String decrypt(String message,String key);
}
