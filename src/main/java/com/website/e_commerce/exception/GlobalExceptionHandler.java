package com.website.e_commerce.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        errorDetail.setProperty("description", "The username or password is incorrect");
        return errorDetail;
    }

    @ExceptionHandler(AccountStatusException.class)
    public ProblemDetail handleAccountStatusException(AccountStatusException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty("description", "The account is locked");
        return errorDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty("description", "You are not authorized to access this resource");
        return errorDetail;
    }

//    @ExceptionHandler(SignatureException.class)
//    public ProblemDetail handleSignatureException(SignatureException exception) {
//        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
//        errorDetail.setProperty("description", "The JWT signature is invalid");
//        return errorDetail;
//    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty("description", "The JWT token has expired");
        return errorDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception exception) {
        // Send stack trace to observability tool
        exception.printStackTrace();

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        errorDetail.setProperty("description", "Unknown internal server error.");
        return errorDetail;
    }

    //product exception handling
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(ProductNotFoundException exception) {
        return createProblemDetailResponse(HttpStatus.NOT_FOUND, exception.getMessage(), "product not found");
    }
    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ProblemDetail> handelProductAlreadyExistException(ProductAlreadyExistException exception){
        return createProblemDetailResponse(HttpStatus.CONFLICT , exception.getMessage()  , "product already exist!");
    }
    // cart exception handlers
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCartNotFoundException(CartNotFoundException exception){
        return createProblemDetailResponse(HttpStatus.NOT_FOUND , exception.getMessage()  , "cart not found.");
    }
    //category exception handlers
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ProblemDetail>handleAlreadyExistException(AlreadyExistException exception){
        return createProblemDetailResponse(HttpStatus.CONFLICT , exception.getMessage() , "create category ? ");

    }



    private ResponseEntity<ProblemDetail> createProblemDetailResponse(HttpStatus status, String message, String description) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(status, message);
        errorDetail.setProperty("description", description);
        return new ResponseEntity<>(errorDetail, status);
    }


}
