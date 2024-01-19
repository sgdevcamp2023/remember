package harmony.communityservice.common.advice;

import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllControllerAdvice {

    @ExceptionHandler(NotFoundDataException.class)
    public BaseResponse<?> exceptionHandler(NotFoundDataException e) {
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("NOT_FOUND_DATA", 5000, "해당하는 데이터를 찾을 수 없습니다."));
    }
}
