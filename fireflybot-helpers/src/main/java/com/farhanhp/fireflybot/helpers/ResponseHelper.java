package com.farhanhp.fireflybot.helpers;

import com.farhanhp.fireflybot.models.ControllerListResponse;
import com.farhanhp.fireflybot.models.ControllerResponse;

import java.util.List;

public interface ResponseHelper {

  <T> ControllerResponse<T> createSuccessResponse(T body);

  ControllerResponse<?> createSuccessResponseFromThrowable(Throwable throwable);

  <T, U extends List<T>> ControllerListResponse<T> createSuccessResponse(U bodies);
}
