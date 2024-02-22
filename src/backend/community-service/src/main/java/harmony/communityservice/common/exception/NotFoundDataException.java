package harmony.communityservice.common.exception;

public class NotFoundDataException extends RuntimeException{
    public NotFoundDataException() {
        super();
    }

    public NotFoundDataException(String message) {
        super(message);
    }

    public NotFoundDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundDataException(Throwable cause) {
        super(cause);
    }

    protected NotFoundDataException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
