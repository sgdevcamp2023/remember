package harmony.communityservice.common.advice;

import static harmony.communityservice.common.utils.LoggingUtils.errorLogging;

import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.exception.IllegalGcsException;
import harmony.communityservice.common.exception.NotFoundDataException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AllControllerAdvice {

    @ExceptionHandler(NotFoundDataException.class)
    public BaseResponse<?> exceptionHandler(NotFoundDataException e, HttpServletRequest request) {
        errorLogging(request, "NotFoundDataException");
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("NOT_FOUND_DATA", 5000, "해당하는 데이터를 찾을 수 없습니다"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<BaseExceptionResponse> exceptionHandler(HttpRequestMethodNotSupportedException e,
                                                                HttpServletRequest request) {
        errorLogging(request, "HttpRequestMethodNotSupportedException");
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", new BaseExceptionResponse(
                "INVALID_INPUT", 5001, "잘못된 입력입니다"));
    }

    @ExceptionHandler(IllegalGcsException.class)
    public BaseResponse<BaseExceptionResponse> exceptionHandler(IllegalGcsException e, HttpServletRequest request) {
        errorLogging(request, "IllegalGcsException");
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("INVALID_GCS_REQUEST", 5002, "잘못된 GCS 요청입니다"));
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public BaseResponse<BaseExceptionResponse> exceptionHandler(HttpMessageNotReadableException e,
//                                                                HttpServletRequest request) {
//        errorLogging(request,"HttpMessageNotReadableException");
//        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", new BaseExceptionResponse())
//    }

}
