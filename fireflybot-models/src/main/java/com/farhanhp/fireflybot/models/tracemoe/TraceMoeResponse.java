package com.farhanhp.fireflybot.models.tracemoe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraceMoeResponse<T> {

  private String error;

  private List<T> result;
}
