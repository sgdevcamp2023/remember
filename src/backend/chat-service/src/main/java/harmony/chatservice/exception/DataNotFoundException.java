package harmony.chatservice.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException{

    private final ExceptionStatus exceptionStatus;

    public DataNotFoundException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
