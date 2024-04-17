package com.farhanhp.fireflybot.comamnds.impl;

import com.farhanhp.fireflybot.clients.TelegramBotClient;
import com.farhanhp.fireflybot.commands.DownloadTelegramImageCommand;
import com.farhanhp.fireflybot.helpers.ClientHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DownloadTelegramImageCommandImpl implements DownloadTelegramImageCommand {

  private final TelegramBotClient telegramBotClient;

  private final ClientHelper clientHelper;

  @Override
  public Mono<byte[]> execute(String fileId) {
    return telegramBotClient.getFile(fileId)
        .flatMap(clientHelper::validateResponse)
        .flatMap(fileResponse -> telegramBotClient.downloadFile(fileResponse.getFilePath()));
  }
}
