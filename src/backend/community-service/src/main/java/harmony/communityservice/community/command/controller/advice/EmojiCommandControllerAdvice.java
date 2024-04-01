package harmony.communityservice.community.command.controller.advice;

import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.exception.DuplicatedEmojiException;
import harmony.communityservice.common.utils.LoggingUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmojiCommandControllerAdvice {

    @ExceptionHandler(DuplicatedEmojiException.class)
    public BaseResponse<BaseExceptionResponse> exceptionHandler(DuplicatedEmojiException e, HttpServletRequest request) {
        LoggingUtils.printLog(request,"DuplicatedEmojiException", false);
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("DUPLICATED_EMOJI_REQUEST", 1000, "같은 이모지를 추가하실 수 없습니다")
        );
    }
}
