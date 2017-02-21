package com.bit2017.mysite.exception;

public class UserDaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDaoException( String message) {
		super( message );
		
	}
	
	public UserDaoException () {
		super( "UserDao Exception occured" );
	}
	
}
