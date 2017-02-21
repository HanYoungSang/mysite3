package com.bit2017.mysite.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String handleException ( Exception e ) {
		// 1. log 처리
		e.printStackTrace();
		System.out.println("ERRE 처리");
		// 2. 화면 처리 (사용자에게 사과..)
		return "error/exception";
	}
	
}
