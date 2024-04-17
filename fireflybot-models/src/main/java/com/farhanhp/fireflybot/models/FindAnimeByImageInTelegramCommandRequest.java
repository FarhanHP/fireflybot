package com.farhanhp.fireflybot.models;

import com.farhanhp.fireflybot.models.telegram.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAnimeByImageInTelegramCommandRequest {

  private Update update;

  private String secretToken;
}
