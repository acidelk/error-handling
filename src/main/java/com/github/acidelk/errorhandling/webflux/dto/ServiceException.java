package com.github.acidelk.errorhandling.webflux.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ServiceException extends RuntimeException {

	private ErrorResponse errorResponse;

	public ServiceException(ErrorResponse errorResponse) {
		super(errorResponse.getError().getMessage());
		this.errorResponse = errorResponse;
	}

	public ServiceException(HttpStatus httpStatus, int errorCode, String msg, String errorClass) {
		super(msg);
		this.errorResponse = new ErrorResponse(httpStatus, errorCode, msg, errorClass);
	}

	public ServiceException(String msg) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, -1, msg, null);
	}

	public ServiceException(int errorCode, String msg) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, msg, null);
	}

	public ServiceException(int errorCode, String msg, String errorClass) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, msg, errorClass);
	}

	public ServiceException(HttpStatus httpStatus, int errorCode, String msg) {
		this(httpStatus, errorCode, msg, null);
	}
}
