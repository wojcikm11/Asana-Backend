package pl.edu.pw.app.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({ AccessDeniedException.class })
//    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>(
//                "You do not have permissions to perform this action", new HttpHeaders(), HttpStatus.FORBIDDEN
//        );
//    }
//
//    @ExceptionHandler({ NoSuchElementException.class })
//    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>(
//                "TODO", new HttpHeaders(), HttpStatus.NOT_FOUND
//        );
//    }
//
//    @ExceptionHandler({ DataIntegrityViolationException.class })
//    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>(
//                "TODO", new HttpHeaders(), HttpStatus.CONFLICT
//        );
//    }
//
//    @ExceptionHandler({ Exception.class, RuntimeException.class })
//    public final ResponseEntity<Object> handleOtherExceptions(Exception e , WebRequest request) {
//        HttpHeaders headers = new HttpHeaders();
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        List<String> errors = Collections.singletonList(e.getMessage());
//        return new ResponseEntity<>(errors, headers, status);
//    }
}
