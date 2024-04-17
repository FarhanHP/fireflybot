package com.farhanhp.fireflybot.comamnds.impl;

import com.farhanhp.fireflybot.clients.TelegramBotClient;
import com.farhanhp.fireflybot.commands.CommandExecutor;
import com.farhanhp.fireflybot.commands.FindAnimeByImageCommand;
import com.farhanhp.fireflybot.commands.FindAnimeByImageInTelegramCommand;
import com.farhanhp.fireflybot.helpers.UrlHelper;
import com.farhanhp.fireflybot.models.FindAnimeByImageInTelegramCommandRequest;
import com.farhanhp.fireflybot.models.telegram.Message;
import com.farhanhp.fireflybot.models.telegram.PhotoSize;
import com.farhanhp.fireflybot.models.telegram.SendMessageRequest;
import com.farhanhp.fireflybot.models.telegram.SendVideoRequest;
import com.farhanhp.fireflybot.models.telegram.TelegramResponse;
import com.farhanhp.fireflybot.models.tracemoe.AnimeFindingResult;
import com.farhanhp.fireflybot.properties.TelegramBotProperties;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import reactor.core.publisher.Mono;

import java.util.List;
import reactor.util.function.Tuple2;

@Component
@RequiredArgsConstructor
public class FindAnimeByImageInTelegramCommandImpl implements FindAnimeByImageInTelegramCommand {

  private static final String HTML = "HTML";

  private static final String NO_ANIME_FOUND = "No Anime Found :(";

  private static final String SUCCESS = "success";

  private static final String MESSAGE_CANNOT_BE_NULL = "Message cannot be null";

  private static final String PHOTO_CANNOT_BE_NULL = "Photo cannot be null";

  private static final String INVALID_WEBHOOK_TOKEN = "Invalid webhook token";

  private static final String ONLY_PERSONAL_CHAT_ALLOWED = "Only personal chat allowed";

  private static final String PRIVATE = "private";

  private final TelegramBotClient telegramBotClient;

  private final CommandExecutor commandExecutor;

  private final UrlHelper urlHelper;

  private final TelegramBotProperties telegramBotProperties;

  @Override
  public Mono<String> execute(FindAnimeByImageInTelegramCommandRequest request) {

    if(!telegramBotProperties.getWebhookSecretToken().equals(request.getSecretToken())) {

      return Mono.error(Forbidden.create(HttpStatus.FORBIDDEN, INVALID_WEBHOOK_TOKEN, null, null, null));
    }

    Message message = request.getUpdate().getMessage();

    if (message == null) {

      return Mono.error(
          BadRequest.create(HttpStatus.BAD_REQUEST, MESSAGE_CANNOT_BE_NULL, null, null, null));
    }

    if(!PRIVATE.equals(message.getChat().getType())) {

      return Mono.error(
          BadRequest.create(HttpStatus.BAD_REQUEST, ONLY_PERSONAL_CHAT_ALLOWED, null, null, null));
    }

    List<PhotoSize> photoSizes = message.getPhoto();

    if (CollectionUtils.isEmpty(photoSizes)) {

      return Mono.error(
          BadRequest.create(HttpStatus.BAD_REQUEST, PHOTO_CANNOT_BE_NULL, null, null, null));
    }

    String chatId = message.getChat().getId();

    return Mono.just(urlHelper.createImageUrlByTelegramFileId(photoSizes.getLast().getFileId()))
        .flatMap(imageUrl -> commandExecutor.execute(FindAnimeByImageCommand.class, imageUrl))
        .flatMap(results -> results.stream()
            .max(Comparator.comparingDouble(AnimeFindingResult::getSimilarity))
            .map(result -> tellResultToTelegram(chatId, result))
            .orElseGet(() -> tellNoAnimeFoundToTelegram(chatId)).flatMap(this::validateResponses));
  }

  private Mono<Tuple2<TelegramResponse<Message>, TelegramResponse<Message>>> tellResultToTelegram(
      String chatId, AnimeFindingResult result) {

    return Mono.zip(
        telegramBotClient.sendMessage(createSendMessageRequest(chatId, createHtmlMessage(result))),
        telegramBotClient.sendVideo(createSendVideoRequest(chatId, result)));
  }

  private SendVideoRequest createSendVideoRequest(String chatId, AnimeFindingResult result) {

    return SendVideoRequest.builder().chatId(chatId).thumbnail(result.getImage())
        .video(result.getVideo()).build();
  }

  private Mono<Tuple2<TelegramResponse<Message>, TelegramResponse<Message>>> tellNoAnimeFoundToTelegram(
      String chatId) {

    return Mono.zip(telegramBotClient.sendMessage(createSendMessageRequest(chatId, NO_ANIME_FOUND)),
        Mono.empty());
  }

  private Mono<String> validateResponses(
      Tuple2<TelegramResponse<Message>, TelegramResponse<Message>> responsesPair) {

    return getErrorResponse(responsesPair).map(this::toError).orElseGet(() -> Mono.just(SUCCESS));
  }

  private Optional<TelegramResponse<Message>> getErrorResponse(
      Tuple2<TelegramResponse<Message>, TelegramResponse<Message>> responsesPair) {

    return Stream.of(responsesPair.getT1(), responsesPair.getT2()).filter(res -> !res.isOk())
        .findFirst();
  }

  private Mono<String> toError(TelegramResponse<Message> response) {

    return Mono.error(
        BadRequest.create(HttpStatus.BAD_REQUEST, response.getDescription(), null, null, null));
  }

  private SendMessageRequest createSendMessageRequest(String chatId, String text) {

    return SendMessageRequest.builder().chatId(chatId).text(text).parseMode(HTML).build();
  }

  private String createHtmlMessage(AnimeFindingResult result) {

    return String.format("title: <b>%s</b> \nepisode: %s \nsimilarity: %f",
        result.getFilename(), result.getEpisode(), result.getSimilarity());
  }
}
