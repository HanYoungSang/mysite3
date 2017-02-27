package com.bit2017.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 *  Logger 생성
	 */
	private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );
	
	
	@ExceptionHandler(Exception.class)
	public String handleException ( Exception e ) {
		// 1. log 처리
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.error( errors.toString() );
		
		System.out.println("ERRE 처리");
		// 2. 화면 처리 (사용자에게 사과..)
		return "error/exception";
	}
	
}
