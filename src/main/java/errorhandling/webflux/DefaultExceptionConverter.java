package errorhandling.webflux;

import org.springframework.http.HttpStatus;
import errorhandling.webflux.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DefaultExceptionConverter {

	private String serviceName;
	private Map<Class<? extends Exception>, Function<Exception, ErrorResponse>> converters = new HashMap<>();
	private Function<Exception, ErrorResponse> defaultHandler = defaultHandler();

	public DefaultExceptionConverter(String serviceName) {
		this.serviceName = serviceName;
	}

	ErrorResponse convert(Exception ex) {
		return converters.getOrDefault(ex.getClass(), defaultHandler)
				.apply(ex)
				.setServiceNameIfEmpty(serviceName);
	}

	private Function<Exception, ErrorResponse> defaultHandler() {
		return e -> new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, -1, e.getMessage(), e.getClass().getSimpleName(), serviceName);
	}

	public DefaultExceptionConverter withDefaultHandler(Function<Exception, ErrorResponse> handler) {
		this.defaultHandler = handler;
		return this;
	}

	public DefaultExceptionConverter withHandler(Class<? extends Exception> exceptionClass, Function<Exception, ErrorResponse> handler) {
		converters.put(exceptionClass, handler);
		return this;
	}
}
