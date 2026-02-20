package carnetdecontacts.crypto;

public final class CryptoException extends RuntimeException {

	private static final long serialVersionUID = -8899440530871670767L;

	public CryptoException(String message) {
		super(message);
	}
	
	public CryptoException(String message,Throwable cause) {
		super(message,cause);
	}

	
}
