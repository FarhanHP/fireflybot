package com.farhanhp.fireflybot.helpers;

import com.farhanhp.fireflybot.models.telegram.TelegramResponse;
import com.farhanhp.fireflybot.models.tracemoe.TraceMoeResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientHelper {

  <T> Mono<T> toMonoResponse(ClientResponse response, ParameterizedTypeReference<T> clazz);

  <T> Mono<T> toMonoResponse(ClientResponse response, Class<T> clazz);

  <T> Mono<List<T>> validateResponse(TraceMoeResponse<T> tracemoeResponse);

  <T> Mono<T> validateResponse(TelegramResponse<T> telegramResponse);
}
