package com.farhanhp.fireflybot.clients;

import com.farhanhp.fireflybot.models.tracemoe.AnimeFindingResult;
import com.farhanhp.fireflybot.models.tracemoe.TraceMoeResponse;
import reactor.core.publisher.Mono;

public interface TraceMoeClient {

  Mono<TraceMoeResponse<AnimeFindingResult>> findAnimeByImage(String imageUrl);
}
