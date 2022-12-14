package pl.opinion_collector.backend.logic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.opinion_collector.backend.logic.exception.type.*;

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

    @ExceptionHandler({InvalidBusinessArgumentException.class,
            ParameterException.class,
            InvalidDataIdException.class,
            DuplicatedDataException.class})
    public final ResponseEntity<Object> handleException(RuntimeException exception) {
        return handleResponse(HttpStatus.NOT_ACCEPTABLE, exception);
    }
    @ExceptionHandler({AuthException.class})
    public final ResponseEntity<Object> handleAuth(RuntimeException exception) {
        return handleResponse(HttpStatus.UNAUTHORIZED, exception);
    }
    @ExceptionHandler({ForbiddenException.class})
    public final ResponseEntity<Object> handleForbiddenException(RuntimeException exception) {
        return handleResponse(HttpStatus.FORBIDDEN, exception);
    }
    @ExceptionHandler({EntityNotFoundException.class})
    public final ResponseEntity<Object> handleNotFound(RuntimeException exception) {
        return handleResponse(HttpStatus.NOT_FOUND, exception);
    }
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(RuntimeException exception) {
        return handleResponse(HttpStatus.BAD_REQUEST, exception);
    }

    public final ResponseEntity<Object> handleResponse(HttpStatus httpStatus, RuntimeException exception) {
        final String error = "Status Code: " + httpStatus.value() +
                ", Exception: " + exception.getClass().getSimpleName();
        return new ResponseEntity<>(new ApiError(httpStatus, exception.getLocalizedMessage(), error),
                new HttpHeaders(),
                httpStatus);
    }


}
