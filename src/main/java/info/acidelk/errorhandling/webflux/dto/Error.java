package info.acidelk.errorhandling.webflux.dto;

import lombok.*;

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
