package com.github.acidelk.errorhandling.webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	@JsonIgnore
	@Setter
	private HttpStatus httpStatus;
	private Error error;

	public ErrorResponse(HttpStatus httpStatus, int code, String message, String errorClass) {
		this.httpStatus = httpStatus;
		this.error = new Error(code, message, errorClass);
	}

	public ErrorResponse(HttpStatus httpStatus, int code, String message, String errorClass, String serviceName) {
		this.httpStatus = httpStatus;
		this.error = new Error(code, message, errorClass, serviceName);
	}

	/**
	 * Null safe getter for error
	 *
	 * @return this.Error or new Error
	 */
	public Error getError() {
		return this.error == null ? this.error = new Error() : this.error;
	}

	public ErrorResponse setServiceNameIfEmpty(String serviceName) {
		if (this.getError().getServiceName() == null) {
			this.getError().setServiceName(serviceName);
		}
		return this;
	}
}
