package com.farhanhp.fireflybot.properties;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("com.farhanhp.fireflybot.telegram.bot")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class TelegramBotProperties {

  private String token;

  private String webhookSecretToken;
}
