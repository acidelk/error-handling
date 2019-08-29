package com.github.acidelk.errorhandling.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.acidelk.errorhandling.webflux.dto.ErrorResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Slf4j
public class WebFluxRestExceptionHandler extends DefaultErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;
	private final DefaultExceptionConverter converter;

	public WebFluxRestExceptionHandler(
		ErrorAttributes errorAttributes,
		ResourceProperties resourceProperties,
		ServerProperties serverProperties,
		ApplicationContext applicationContext,
		DefaultExceptionConverter converter,
		ObjectMapper objectMapper,
		ServerCodecConfigurer serverCodecConfigurer
	) {
		super(errorAttributes, resourceProperties, serverProperties.getError(), applicationContext);
		this.converter = converter;
		this.objectMapper = objectMapper;
		this.setMessageReaders(serverCodecConfigurer.getReaders());
		this.setMessageWriters(serverCodecConfigurer.getWriters());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	@Override
	protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Throwable error = getError(request);
		log.error("Handle error: ", error);
		ErrorResponse response = converter.convert((Exception) error);
		return ServerResponse
			.status(response.getHttpStatus())
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(BodyInserters.fromObject(response))
			.doOnNext(errResponse -> logError(response));
	}

	@SneakyThrows
	private void logError(ErrorResponse response) {
		log.error("Response body: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
	}
}
