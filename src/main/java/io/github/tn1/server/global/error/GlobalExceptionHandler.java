package io.github.tn1.server.global.error;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponse> serverExceptionHandler(ServerException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode.getStatus(), errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse>
	methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    	return new ResponseEntity<>(new ErrorResponse(400,
				e.getBindingResult().getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ErrorResponse>
	propertyReferenceExceptionHandler(PropertyReferenceException e) {
    	return new ResponseEntity<>(new ErrorResponse(400,
				e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<ErrorResponse> multipartExceptionExceptionHandler(MultipartException e) {
    	return new ResponseEntity<>(new ErrorResponse(400,
				"Not Multipart File"), HttpStatus.BAD_REQUEST);
	}

}
