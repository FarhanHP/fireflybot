package com.farhanhp.fireflybot.models.tracemoe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimeFindingResult {

  private String filename;

  private Double similarity;

  private Integer episode;

  private String video;

  private String image;
}
