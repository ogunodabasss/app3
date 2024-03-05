package com.example.app3.m1.config.excaptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

@Slf4j(topic = "ProdGlobalExceptionHandler")
//@Profile(AppProfiles.TEST)
@RestControllerAdvice
public class TestGlobalExceptionHandler extends AbstractGlobalExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleSQLException(SQLException exception, WebRequest request) {
		log.error(exception.getMessage(), exception);
		return super.buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
	}

}