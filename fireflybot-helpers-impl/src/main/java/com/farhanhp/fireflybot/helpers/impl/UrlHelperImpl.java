package com.farhanhp.fireflybot.helpers.impl;

import com.farhanhp.fireflybot.helpers.UrlHelper;
import com.farhanhp.fireflybot.properties.FireflyBotProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlHelperImpl implements UrlHelper {

  private final FireflyBotProperties fireflyBotProperties;

  @Override
  public String createImageUrlByTelegramFileId(String fileId) {
    return fireflyBotProperties.getBaseUrl() + "/api/image/telegram/" + fileId;
  }
}
