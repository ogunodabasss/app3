package com.example.app3.m1.config.excaptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@Slf4j(topic = "AbstractGlobalExceptionHandler")
@RestControllerAdvice
public abstract class AbstractGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<Object> handleNoSuchElementFoundException(RuntimeException exception, WebRequest request) {
		log.error(exception.getMessage(), exception);
		return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected abstract ResponseEntity<Object> handleSQLException(SQLException exception, WebRequest request);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
		log.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	protected final ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatusCode httpStatus,
			WebRequest request) {
		return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
	}

	protected final ResponseEntity<Object> buildErrorResponse(Exception exception, String message,
					HttpStatusCode httpStatus, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
		StringBuilder builder = new StringBuilder(0);
		for (var o : exception.getStackTrace())
			builder.append(o).append(System.lineSeparator());
		errorResponse.setStackTrace(builder.toString());

		return ResponseEntity.status(httpStatus).body(errorResponse);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		return buildErrorResponse(ex, statusCode, request);
	}

}