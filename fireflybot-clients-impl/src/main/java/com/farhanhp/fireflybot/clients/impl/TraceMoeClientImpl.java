package com.farhanhp.fireflybot.clients.impl;

import com.farhanhp.fireflybot.clients.TraceMoeClient;
import com.farhanhp.fireflybot.helpers.ClientHelper;
import com.farhanhp.fireflybot.models.tracemoe.AnimeFindingResult;
import com.farhanhp.fireflybot.models.tracemoe.TraceMoeResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TraceMoeClientImpl implements TraceMoeClient {

  private static final String BASE_URL = "https://api.trace.moe";

  private final WebClient webClient = WebClient.create(BASE_URL);

  private final ClientHelper clientHelper;

  @Override
  public Mono<TraceMoeResponse<AnimeFindingResult>> findAnimeByImage(String imageUrl) {
    return webClient.get()
        .uri("/search?url=" + imageUrl)
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(
            response -> clientHelper.toMonoResponse(response, new ParameterizedTypeReference<>() {
            }));
  }
}
