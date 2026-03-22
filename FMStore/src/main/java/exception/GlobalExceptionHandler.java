package exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import payload.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException e){
		HttpStatus s= (HttpStatus) e.getStatusCode();
		
		ApiError error= new ApiError(LocalDateTime.now(), s.value(), e.getMessage());
		return new ResponseEntity<>(error,s);
		// cast perche ResponseEntity vuole httpStatus 
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception e){
		ApiError error= new ApiError(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
