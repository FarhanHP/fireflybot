package com.farhanhp.fireflybot.helpers.impl;

import com.farhanhp.fireflybot.helpers.ClientHelper;
import com.farhanhp.fireflybot.models.telegram.TelegramResponse;
import com.farhanhp.fireflybot.models.tracemoe.TraceMoeResponse;
import io.micrometer.common.util.StringUtils;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class ClientHelperImpl implements ClientHelper {

  @Override
  public <T> Mono<T> toMonoResponse(ClientResponse response,
      ParameterizedTypeReference<T> typeReference) {

    if (response.statusCode() == HttpStatus.OK) {
      return response.bodyToMono(typeReference);
    }

    return response.createException().flatMap(Mono::error);

  }

  @Override
  public <T> Mono<T> toMonoResponse(ClientResponse response, Class<T> clazz) {

    if (response.statusCode() == HttpStatus.OK) {
      return response.bodyToMono(clazz);
    }

    return response.createException().flatMap(Mono::error);

  }

  @Override
  public <T> Mono<List<T>> validateResponse(TraceMoeResponse<T> tracemoeResponse) {

    if (StringUtils.isBlank(tracemoeResponse.getError())) {
      return Mono.just(tracemoeResponse.getResult());
    }

    return Mono.error(
        BadRequest.create(HttpStatus.BAD_REQUEST, tracemoeResponse.getError(), null, null, null));
  }

  @Override
  public <T> Mono<T> validateResponse(TelegramResponse<T> telegramResponse) {

    if (telegramResponse.isOk()) {
      return Mono.just(telegramResponse.getResult());
    }

    return Mono.error(
        BadRequest.create(HttpStatus.BAD_REQUEST, telegramResponse.getDescription(), null, null,
            null));
  }
}
