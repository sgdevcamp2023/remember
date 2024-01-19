package harmony.communityservice.common.advice;

import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.exception.IllegalGcsException;
import harmony.communityservice.common.exception.NotFoundDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllControllerAdvice {

    @ExceptionHandler(NotFoundDataException.class)
    public BaseResponse<?> exceptionHandler(NotFoundDataException e) {
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("NOT_FOUND_DATA", 5000, "해당하는 데이터를 찾을 수 없습니다"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<BaseExceptionResponse> exceptionHandler(HttpRequestMethodNotSupportedException e) {
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", new BaseExceptionResponse(
                "INVALID_INPUT", 5001, "잘못된 입력입니다"));
    }

    @ExceptionHandler(IllegalGcsException.class)
    public BaseResponse<BaseExceptionResponse> exceptionHandler(IllegalGcsException e) {
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("INVALID_GCS_REQUEST",5002,"잘못된 GCS 요청입니다"));
    }
}
