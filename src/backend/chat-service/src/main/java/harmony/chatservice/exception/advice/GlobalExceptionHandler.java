package harmony.chatservice.exception.advice;

import harmony.chatservice.exception.DataNotFoundException;
import harmony.chatservice.exception.ExceptionResponse;
import harmony.chatservice.exception.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @MessageExceptionHandler(DataNotFoundException.class)
    @SendToUser(destinations="/queue/errors", broadcast=false)
    public ExceptionResponse dataNotFoundExceptionHandler(DataNotFoundException e) {

        return new ExceptionResponse(e.getExceptionStatus().getStatusCode(),
                        e.getExceptionStatus().getMessage(),
                        e.getExceptionStatus().getDesc());
    }

    /**
     * 파일 업로드 관련 예외 처리
     */
    @ExceptionHandler(FileException.class)
    public ExceptionResponse handleFileException(FileException e) {

        return new ExceptionResponse(e.getExceptionStatus().getStatusCode(),
                e.getExceptionStatus().getMessage(),
                e.getExceptionStatus().getDesc());
    }
}