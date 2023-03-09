package com.songyuankun.wechat.blog.application.handler;

public interface CommandHandler<T, R> {
    R handle(T command);
}
