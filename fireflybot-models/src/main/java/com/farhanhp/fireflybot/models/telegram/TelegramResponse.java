package com.farhanhp.fireflybot.models.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramResponse<T> {

  private boolean ok;

  private T result;

  private String description;
}
