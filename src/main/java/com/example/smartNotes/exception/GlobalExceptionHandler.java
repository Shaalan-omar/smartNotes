package com.example.smartNotes.exception;

import com.example.smartNotes.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice //This class is a global interceptor for all controllers that catches exceptions thrown during request handling and automatically has its response as JSON
public class GlobalExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ApiError> handleNoteNotFound(NoteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(404, ex.getMessage(), null)); //Leeh mast5dmtsh ResponseEntity 3alla toul
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        ApiError body = new ApiError(404, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fe -> fe.getField(),
                        fe -> fe.getDefaultMessage(),
                        (a, b) -> a
                ));

        return ResponseEntity.badRequest()
                .body(new ApiError(400, "Validation failed", fieldErrors));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleFallback(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ApiError(500, "Unexpected error", null));
//    }
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleFallback(Exception ex) {
            ex.printStackTrace(); // TEMP: print stack trace in console
            ApiError body = new ApiError(500,
                    ex.getClass().getSimpleName() + ": " + ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }

}
