package harmony.chatservice.exception;

import lombok.Getter;

@Getter
public class FileException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public FileException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}