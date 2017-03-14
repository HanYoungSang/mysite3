package com.bit2017.mysite.exception;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PostDaoException extends SQLException {
	private static final long serialVersionUID = 1L;
	/**
	 *  Logger 생성
	 */
	private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );
	
	public PostDaoException () {
		super( "PostDaoException Exception occured" );
		LOG.error("PostDaoException");
	}
	
	public PostDaoException( String message) {
		LOG.error("PostDaoException");
		
	}
	
}
