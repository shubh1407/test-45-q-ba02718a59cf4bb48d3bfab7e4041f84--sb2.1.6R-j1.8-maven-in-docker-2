package org.codejudge.sb.exception;

public class BadRequestException extends RuntimeException {

	public BadRequestException() {
		
	}

	public BadRequestException(String arg0) {
		super(arg0);
		
	}

	public BadRequestException(Throwable arg0) {
		super(arg0);
		
	}

	public BadRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	public BadRequestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		
	}

}
