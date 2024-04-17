package com.farhanhp.fireflybot.helpers.impl;

import com.farhanhp.fireflybot.helpers.ResponseHelper;
import com.farhanhp.fireflybot.models.ControllerListResponse;
import com.farhanhp.fireflybot.models.ControllerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseHelperImpl implements ResponseHelper {

  @Override
  public <T> ControllerResponse<T> createSuccessResponse(T body) {
    return ControllerResponse.<T>builder()
        .code(HttpStatus.OK.value())
        .message(HttpStatus.OK.toString())
        .body(body)
        .build();
  }

  @Override
  public ControllerResponse<?> createSuccessResponseFromThrowable(Throwable throwable) {
    return ControllerResponse.builder()
        .code(HttpStatus.OK.value())
        .message(HttpStatus.OK.toString())
        .error(throwable.getMessage())
        .build();
  }

  @Override
  public <T, U extends List<T>> ControllerListResponse<T> createSuccessResponse(U bodies) {
    return ControllerListResponse.<T>builder()
        .code(HttpStatus.OK.value())
        .message(HttpStatus.OK.toString())
        .bodies(bodies)
        .build();
  }
}
