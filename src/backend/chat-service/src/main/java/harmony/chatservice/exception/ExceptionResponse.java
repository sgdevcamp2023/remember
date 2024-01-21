package harmony.chatservice.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private int statusCode;
    private String message;
    private String desc;

    public ExceptionResponse(int statusCode, String message, String desc) {
        this.statusCode = statusCode;
        this.message = message;
        this.desc = desc;
    }
}