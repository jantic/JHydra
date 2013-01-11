package jhydra.core.config.jydra.core.config.exceptions;

import jhydra.core.exceptions.FatalException;

/**
 * User: jantic
 * Date: 1/11/13
 */
public class InvalidEmailAddressException extends FatalException {
    private final String emailAddress;
    private final String details;

    public InvalidEmailAddressException(String emailAddress, String details){
        super("");
        this.emailAddress = emailAddress;
        this.details = details;
    }

    public String getMessage(){
        return "Email address attempted is not valid.  Attempted: " + emailAddress + ".  Details: " + details;
    }
}
