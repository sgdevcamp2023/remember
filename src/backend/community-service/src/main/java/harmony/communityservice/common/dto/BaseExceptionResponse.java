package harmony.communityservice.common.dto;

import lombok.Getter;

@Getter
public class BaseExceptionResponse {
    private int code;
    private String exception;
    private String message;

    public BaseExceptionResponse(String exception, int code, String message) {
        this.exception = exception;
        this.code = code;
        this.message = message;
    }
}
