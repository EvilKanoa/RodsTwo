package ca.kanoa.RodsTwo.Objects;

public class InvalidRecipeException extends Exception {
    
	private static final long serialVersionUID = -7953608719397058650L;

	public InvalidRecipeException(String exception){
        super(exception);
    }
	
}
