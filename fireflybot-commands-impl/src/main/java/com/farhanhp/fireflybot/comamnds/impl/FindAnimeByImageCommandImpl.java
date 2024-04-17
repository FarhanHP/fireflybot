package com.farhanhp.fireflybot.comamnds.impl;

import com.farhanhp.fireflybot.clients.TraceMoeClient;
import com.farhanhp.fireflybot.commands.FindAnimeByImageCommand;
import com.farhanhp.fireflybot.helpers.ClientHelper;
import com.farhanhp.fireflybot.models.tracemoe.AnimeFindingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAnimeByImageCommandImpl implements FindAnimeByImageCommand {

  private final TraceMoeClient traceMoeClient;

  private final ClientHelper clientHelper;

  @Override
  public Mono<List<AnimeFindingResult>> execute(String imageUrl) {
    return traceMoeClient.findAnimeByImage(imageUrl)
        .flatMap(clientHelper::validateResponse);
  }
}
