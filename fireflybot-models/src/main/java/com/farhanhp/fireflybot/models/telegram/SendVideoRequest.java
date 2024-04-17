package com.farhanhp.fireflybot.models.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendVideoRequest {

  @JsonProperty("chat_id")
  private String chatId;

  private String video;

  private String thumbnail;
}
