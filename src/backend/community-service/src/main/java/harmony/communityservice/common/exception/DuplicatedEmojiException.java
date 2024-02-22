package harmony.communityservice.common.exception;

public class DuplicatedEmojiException extends RuntimeException{
    public DuplicatedEmojiException() {
        super();
    }

    public DuplicatedEmojiException(String message) {
        super(message);
    }

    public DuplicatedEmojiException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEmojiException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedEmojiException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
