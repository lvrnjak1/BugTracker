package ba.project.bugtracker.exceptions;

import ba.project.bugtracker.exceptions.custom.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiException apiException = new ApiException(NOT_FOUND);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(EmailAlreadyInUse.class)
    public ResponseEntity<Object> handleEmailInUse(
            EmailAlreadyInUse ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_ACCEPTABLE);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(UsernameNotAvailableException.class)
    public ResponseEntity<Object> handleUsernameTaken(
            UsernameNotAvailableException ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_ACCEPTABLE);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(
            UnauthorizedException ex) {
        ApiException apiException = new ApiException(HttpStatus.UNAUTHORIZED);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(
            InvalidCredentialsException ex) {
        System.out.println("Here");
        ApiException apiException = new ApiException(HttpStatus.UNAUTHORIZED);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }
}
