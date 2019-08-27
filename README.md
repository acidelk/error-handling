# Spring boot starter for error handling in webflux microservices
[![Build Status](https://travis-ci.com/acidelk/error-handling.svg?branch=master)](https://travis-ci.com/acidelk/error-handling)
 [ ![Download](https://api.bintray.com/packages/acidelk/maven/error-handling/images/download.svg?version=0.1.1) ](https://bintray.com/acidelk/maven/error-handling/0.1.1/link)

This starter handle all exceptions and log their.

## What response model?
```
{
    "error": {
        "code": -1,
        "message": "Something went wrong"
        "errorClass": "SomethingException",
        "serviceName": "my-best-api"
    }
}
```

## How to use?
build.gradle
```
dependencies {
    compile 'com.github.acidelk:error-handling:{version}'
}

```
## How can i customise handlers for my exceptions?
```
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
