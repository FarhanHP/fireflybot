package com.farhanhp.fireflybot.clients;

import com.farhanhp.fireflybot.models.telegram.File;
import com.farhanhp.fireflybot.models.telegram.Message;
import com.farhanhp.fireflybot.models.telegram.SendMessageRequest;
import com.farhanhp.fireflybot.models.telegram.SendVideoRequest;
import com.farhanhp.fireflybot.models.telegram.TelegramResponse;
import reactor.core.publisher.Mono;

public interface TelegramBotClient {

  Mono<TelegramResponse<File>> getFile(String fileId);

  Mono<TelegramResponse<Message>> sendMessage(SendMessageRequest request);

  Mono<TelegramResponse<Message>> sendVideo(SendVideoRequest request);

  Mono<byte[]> downloadFile(String filePath);
}
