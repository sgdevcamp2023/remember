package harmony.chatservice.dto.response;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final int statusCode;
    private final String message;
    private final String desc;

    public ExceptionResponse(int statusCode, String message, String desc) {
        this.statusCode = statusCode;
        this.message = message;
        this.desc = desc;
    }
}