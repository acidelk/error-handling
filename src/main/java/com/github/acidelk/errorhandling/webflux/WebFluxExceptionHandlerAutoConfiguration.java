package com.github.acidelk.errorhandling.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;

@Configuration
@AutoConfigureBefore(ErrorWebFluxAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxExceptionHandlerAutoConfiguration {

	@Bean
	@Order(-1)
	public WebFluxRestExceptionHandler webFluxRestExceptionHandler(
			ErrorAttributes errorAttributes,
			ResourceProperties resourceProperties,
			ServerProperties serverProperties,
			ApplicationContext applicationContext,
			DefaultExceptionConverter converter,
			ObjectMapper objectMapper,
			ServerCodecConfigurer serverCodecConfigurer
	) {
		return new WebFluxRestExceptionHandler(
				errorAttributes,
				resourceProperties,
				serverProperties,
				applicationContext,
				converter,
				objectMapper,
				serverCodecConfigurer
		);
	}

	@Bean
	@ConditionalOnMissingBean(DefaultExceptionConverter.class)
	public DefaultExceptionConverter defaultExceptionConverter(
			@Value("${spring.application.name}") String applicationName
	) {
		return new DefaultExceptionConverter(applicationName);
	}
}
