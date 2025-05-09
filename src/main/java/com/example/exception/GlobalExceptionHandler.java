package com.example.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<String>handleInvalidAccount(InvalidAccountException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(AccountConflictException.class)
    public ResponseEntity<String> handleAccountConflict(AccountConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(UnauthorizedAccountException.class)
    public ResponseEntity<String> handleUnauthorizedAccountException(UnauthorizedAccountException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMessageException.class)
    public ResponseEntity<String> handleInvalidMessageException(InvalidMessageException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
