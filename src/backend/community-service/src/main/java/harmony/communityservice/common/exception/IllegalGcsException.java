package harmony.communityservice.common.exception;

public class IllegalGcsException extends RuntimeException{
    public IllegalGcsException() {
        super();
    }

    public IllegalGcsException(String message) {
        super(message);
    }

    public IllegalGcsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalGcsException(Throwable cause) {
        super(cause);
    }

    protected IllegalGcsException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
