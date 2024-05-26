package harmony.communityservice.common.exception;

public class WrongThresholdRangeException extends RuntimeException{
    public WrongThresholdRangeException() {
        super();
    }

    public WrongThresholdRangeException(Throwable cause) {
        super(cause);
    }

    public WrongThresholdRangeException(String message) {
        super(message);
    }

    public WrongThresholdRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    protected WrongThresholdRangeException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
