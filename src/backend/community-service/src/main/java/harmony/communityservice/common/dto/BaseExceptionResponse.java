package harmony.communityservice.common.dto;

public record BaseExceptionResponse(
        String exception,
        int code,
        String message
) {
}
