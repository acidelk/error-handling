package com.github.acidelk.errorhandling.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Error {
	private int code;
	private String message;
	private String errorClass;
	private String serviceName;

	public Error(int code, String message, String errorClass) {
		this.code = code;
		this.message = message;
		this.errorClass = errorClass;
	}
}
