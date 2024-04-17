package com.farhanhp.fireflybot.comamnds.impl;

import com.farhanhp.fireflybot.commands.Command;
import com.farhanhp.fireflybot.commands.CommandExecutor;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CommandExecutorImpl implements CommandExecutor {

  private final ApplicationContext applicationContext;

  @Override
  public <T, U, V extends Command<T, U>> Mono<U> execute(Class<V> commandClassName,
      T commandRequest) {
    Command<T, U> command = applicationContext.getBean(commandClassName);
    return command.execute(commandRequest);
  }
}
