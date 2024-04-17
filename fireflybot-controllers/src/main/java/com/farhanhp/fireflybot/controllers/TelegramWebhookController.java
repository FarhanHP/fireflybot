package com.farhanhp.fireflybot.controllers;

import com.farhanhp.fireflybot.commands.CommandExecutor;
import com.farhanhp.fireflybot.commands.FindAnimeByImageInTelegramCommand;
import com.farhanhp.fireflybot.helpers.ResponseHelper;
import com.farhanhp.fireflybot.models.ControllerResponse;
import com.farhanhp.fireflybot.models.FindAnimeByImageInTelegramCommandRequest;
import com.farhanhp.fireflybot.models.telegram.Update;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/webhook/telegram")
@AllArgsConstructor
public class TelegramWebhookController {

  private static final String X_TELEGRAM_BOT_API_SECRET_TOKEN = "X-Telegram-Bot-Api-Secret-Token";

  private CommandExecutor commandExecutor;

  private ResponseHelper responseHelper;

  @PostMapping("/findAnimeByImage")
  public Mono<ControllerResponse<String>> findAnimeByImage(
      @RequestHeader(X_TELEGRAM_BOT_API_SECRET_TOKEN) String secretToken,
      @RequestBody Update update) {

    return commandExecutor.execute(FindAnimeByImageInTelegramCommand.class,
        FindAnimeByImageInTelegramCommandRequest.builder().update(update).secretToken(secretToken)
            .build()).map(responseHelper::createSuccessResponse).subscribeOn(Schedulers.parallel());
  }
}
