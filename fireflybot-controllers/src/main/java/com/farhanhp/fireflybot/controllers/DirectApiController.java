package com.farhanhp.fireflybot.controllers;

import com.farhanhp.fireflybot.commands.CommandExecutor;
import com.farhanhp.fireflybot.commands.DownloadTelegramImageCommand;
import com.farhanhp.fireflybot.commands.FindAnimeByImageCommand;
import com.farhanhp.fireflybot.helpers.ResponseHelper;
import com.farhanhp.fireflybot.models.ControllerListResponse;
import com.farhanhp.fireflybot.models.tracemoe.AnimeFindingResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DirectApiController {

  private CommandExecutor commandExecutor;

  private ResponseHelper responseHelper;

  @GetMapping("/findAnimeByImage")
  public Mono<ControllerListResponse<AnimeFindingResult>> findAnimeByImage(
      @RequestParam(name = "imageUrl") String imageUrl) {
    return commandExecutor.execute(FindAnimeByImageCommand.class, imageUrl)
        .map(responseHelper::createSuccessResponse).subscribeOn(Schedulers.parallel());
  }

  @GetMapping(value = "/image/telegram/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
  public Mono<byte[]> downloadTelegramImage(@PathVariable(name = "fileId") String fileId) {
    return commandExecutor.execute(DownloadTelegramImageCommand.class, fileId)
        .subscribeOn(Schedulers.parallel());
  }
}
