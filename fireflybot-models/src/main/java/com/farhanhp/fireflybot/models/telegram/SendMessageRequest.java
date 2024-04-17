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
public class SendMessageRequest {

  @JsonProperty("chat_id")
  private String chatId;

  private String text;

  @JsonProperty("parse_mode")
  private String parseMode;
}
