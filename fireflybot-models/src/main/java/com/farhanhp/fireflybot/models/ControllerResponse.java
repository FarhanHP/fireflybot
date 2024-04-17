package com.farhanhp.fireflybot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ControllerResponse<T> {

  private Integer code;

  private String message;

  private String error;

  private T body;
}
