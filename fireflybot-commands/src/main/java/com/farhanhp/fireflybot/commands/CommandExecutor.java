package com.farhanhp.fireflybot.commands;

import reactor.core.publisher.Mono;

public interface CommandExecutor {

  <T, U, V extends Command<T, U>> Mono<U> execute(Class<V> commandClassName, T commandRequest);
}
