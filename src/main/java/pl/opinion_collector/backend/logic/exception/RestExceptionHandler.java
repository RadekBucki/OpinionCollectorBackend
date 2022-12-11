package pl.opinion_collector.backend.logic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.opinion_collector.backend.logic.exception.type.AuthException;
import pl.opinion_collector.backend.logic.exception.type.ForbiddenException;
import pl.opinion_collector.backend.logic.exception.type.InvalidBusinessArgumentException;
import pl.opinion_collector.backend.logic.exception.type.ParameterException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + " : " + error.getDefaultMessage());
        }
        for (final ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + " : " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid data provided", errors);

        return handleExceptionInternal(exception, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidBusinessArgumentException.class, ParameterException.class})
    public final ResponseEntity<Object> handleException(
            RuntimeException exception) {
        final String error = "Status Code: " + HttpStatus.NOT_ACCEPTABLE.value() + ", Exception: " + exception.getClass().getSimpleName();

        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_ACCEPTABLE, exception.getLocalizedMessage(), error),
                new HttpHeaders(),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({AuthException.class})
    public final ResponseEntity<Object> handleAuth(RuntimeException exception) {
        final String error = "Status Code: " + HttpStatus.UNAUTHORIZED.value() + ", Exception: " + exception.getClass().getSimpleName();

        return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, exception.getLocalizedMessage(), error),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ForbiddenException.class})
    public final ResponseEntity<Object> handleForbiddenException(RuntimeException exception) {
        final String error = "Status Code: " + HttpStatus.FORBIDDEN.value() + ", Exception: " + exception.getClass().getSimpleName();

        return new ResponseEntity<>(new ApiError(HttpStatus.FORBIDDEN, exception.getLocalizedMessage(), error),
                new HttpHeaders(),
                HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({EntityNotFoundException.class})
    public final ResponseEntity<Object> handleNotFound(RuntimeException exception) {
        final String error = "Status Code: " + HttpStatus.NOT_FOUND.value() + ", Exception: " + exception.getClass().getSimpleName();

        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), error),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }


}
