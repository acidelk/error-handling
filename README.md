# Spring boot starter for error handling in webflux microservices
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/78b9d088b60f4d8fb2ab54c39d63ba7b)](https://app.codacy.com/app/fed.guman/error-handling?utm_source=github.com&utm_medium=referral&utm_content=acidelk/error-handling&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.com/acidelk/error-handling.svg?branch=master)](https://travis-ci.com/acidelk/error-handling)
 [![Maven Central](https://img.shields.io/maven-central/v/com.github.acidelk/error-handling.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.acidelk%22%20AND%20a:%22error-handling%22)

This starter handle all exceptions and log their.

## What response model
```java
{
    "error": {
        "code": -1,
        "message": "Something went wrong"
        "errorClass": "SomethingException",
        "serviceName": "my-best-api"
    }
}
```

## How to use
build.gradle
```gradle
dependencies {
    compile 'com.github.acidelk:error-handling:{version}'
}

```
## How can i customise handlers for my exceptions
```java
@Configuration
public class ErrorHandlerConfiguration {

  @Bean
  public DefaultExceptionConverter defaultExceptionConverter(
      @Value("${spring.application.name}") String application
  ) {
    return new DefaultExceptionConverter(application)
        .withHandler(HttpUnknownException.class, this::handleUnknownException)
        .withDefaultHandler(this::handleDefaultException);
  }

  private ErrorResponse handleDefaultException(Exception e) {
    return new ErrorResponse(HttpStatus.I_AM_A_TEAPOT, -1, e.getMessage(), e.getClass().getSimpleName());
  }

  private ErrorResponse handleUnknownException(Exception e) {
    return ((HttpUnknownException) e).getErrorResponse();
  }
}
```
