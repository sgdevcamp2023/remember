package harmony.communityservice.common.exception;

public class WrongUserException extends RuntimeException{

    public WrongUserException() {
        super();
    }

    public WrongUserException(Throwable cause) {
        super(cause);
    }

    public WrongUserException(String message) {
        super(message);
    }

    public WrongUserException(String message, Throwable cause) {
        super(message, cause);
    }

    protected WrongUserException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
