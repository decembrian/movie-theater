package pdm.persistence.web.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleException(EntityNotFoundException e){
        ExceptionMessage response = new ExceptionMessage(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
