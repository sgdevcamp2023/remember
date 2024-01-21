package harmony.chatservice.exception.advice;

import harmony.chatservice.exception.DataNotFoundException;
import harmony.chatservice.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
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

    //    @ExceptionHandler(DataNotFoundException.class)
//    public ResponseEntity<ExceptionResponse> dataNotFoundExceptionHandler(DataNotFoundException e) {
//        return ResponseEntity
//                .status(e.getExceptionStatus().getStatusCode())
//                .body(new ExceptionResponse(e.getExceptionStatus().getStatusCode(),
//                        e.getExceptionStatus().getMessage(),
//                        e.getExceptionStatus().getDesc()));
//    }
}