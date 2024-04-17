package com.farhanhp.fireflybot.clients.impl;

import com.farhanhp.fireflybot.clients.TelegramBotClient;
import com.farhanhp.fireflybot.helpers.ClientHelper;
import com.farhanhp.fireflybot.models.telegram.File;
import com.farhanhp.fireflybot.models.telegram.Message;
import com.farhanhp.fireflybot.models.telegram.SendMessageRequest;
import com.farhanhp.fireflybot.models.telegram.SendVideoRequest;
import com.farhanhp.fireflybot.models.telegram.TelegramResponse;
import com.farhanhp.fireflybot.properties.TelegramBotProperties;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TelegramBotClientImpl implements TelegramBotClient {

  private final WebClient webClient;

  private final ClientHelper clientHelper;

  private static final String BASE_URL = "https://api.telegram.org";

  private final String tokenUrlSegment;

  @Autowired
  public TelegramBotClientImpl(TelegramBotProperties telegramBotProperties,
      ClientHelper clientHelper) {
    this.clientHelper = clientHelper;

    webClient = WebClient.builder().baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

    tokenUrlSegment = "/bot" + telegramBotProperties.getToken();
  }

  @Override
  public Mono<TelegramResponse<File>> getFile(String fileId) {

    return webClient.get().uri(tokenUrlSegment + "/getFile?file_id=" + fileId)
        .accept(MediaType.APPLICATION_JSON).exchangeToMono(
            response -> clientHelper.toMonoResponse(response, new ParameterizedTypeReference<>() {
            }));
  }

  @Override
  public Mono<TelegramResponse<Message>> sendMessage(SendMessageRequest request) {

    return webClient.post().uri(tokenUrlSegment + "/sendMessage")
        .body(BodyInserters.fromValue(request)).accept(MediaType.APPLICATION_JSON).exchangeToMono(
            response -> clientHelper.toMonoResponse(response, new ParameterizedTypeReference<>() {
            }));
  }

  @Override
  public Mono<byte[]> downloadFile(String filePath) {

    return webClient.get().uri("/file" + tokenUrlSegment + "/" + filePath)
        .accept(MediaType.IMAGE_JPEG)
        .exchangeToMono(response -> clientHelper.toMonoResponse(response, byte[].class));
  }

  @Override
  public Mono<TelegramResponse<Message>> sendVideo(SendVideoRequest request) {

    return webClient.post().uri(tokenUrlSegment + "/sendVideo").accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request)).exchangeToMono(
            response -> clientHelper.toMonoResponse(response, new ParameterizedTypeReference<>() {
            }));
  }
}
