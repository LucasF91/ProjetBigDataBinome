package fr.formation.inti.shop.api.rest.exception;

public class ValidationParameterException extends Throwable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidationParameterException(final String message) {
        super(message);
    }
    public ValidationParameterException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
