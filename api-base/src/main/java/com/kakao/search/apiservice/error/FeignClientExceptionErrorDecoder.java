package com.kakao.search.apiservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

  private static final StringDecoder stringDecoder = new StringDecoder();

  @Override
  public FeignClientException decode(final String methodKey, Response response) {
    String message = "Null Response Body.";
    if (response.body() != null) {
      try {
        message = stringDecoder.decode(response, String.class).toString();
      } catch (IOException e) {
        log.error(
            methodKey + "Error Deserializing response body from failed feign request response.", e);
      }
    }
    return new FeignClientException(response.status(), message, response.headers());
  }
}