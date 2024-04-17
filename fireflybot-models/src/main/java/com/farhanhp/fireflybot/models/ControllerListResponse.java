package com.farhanhp.fireflybot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ControllerListResponse<T> {

  private Integer code;

  private String message;

  private String error;

  private List<T> bodies;
}
