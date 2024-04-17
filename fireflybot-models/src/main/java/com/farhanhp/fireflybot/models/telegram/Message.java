package com.farhanhp.fireflybot.models.telegram;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

  @Nullable
  private List<PhotoSize> photo;

  private Chat chat;
}
