package carnetdecontacts.crypto;

public final class AlphabetCipher {

	public static final String ALPHABETMAJMIN =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public static final String ALPHABETMAJ =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String ALPHABET = ALPHABETMAJMIN+
			"éèàçù@#&'(§!)}°-_/*+%$,;.<>`£ \n";
	
	public static final String NUMBER ="0123456789";
	
	public static final String ALPHABETNUMBER = ALPHABET+NUMBER;
	public static final int LENGTH = ALPHABETNUMBER.length();
	
	
	private AlphabetCipher() {
		super();
	}
	
	
}
