package com.songyuankun.wechat.blog.application.handler;

public interface QueryHandler<T, R> {
    R handle(T query);
}
