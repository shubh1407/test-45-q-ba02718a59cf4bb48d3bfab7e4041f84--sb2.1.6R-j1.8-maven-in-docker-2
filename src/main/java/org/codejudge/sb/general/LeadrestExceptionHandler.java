package org.codejudge.sb.general;

import javax.servlet.http.HttpServletResponse;

import org.codejudge.sb.beans.LeadBeans;
import org.codejudge.sb.beans.LeadErrorResponse;
import org.codejudge.sb.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LeadrestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<LeadErrorResponse> handleException(BadRequestException badRequestException){
		
		System.out.println("handle");
		LeadErrorResponse leadErrorResponse = new LeadErrorResponse("failure",badRequestException.getMessage());
		
		return new ResponseEntity<>(leadErrorResponse,HttpStatus.BAD_REQUEST);
	}
}
