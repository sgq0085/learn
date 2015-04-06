package com.gqshao.redis.service;

/**
 * Redis消息处理统一借口 根据配置指向实现类，并调用统一方法handle
 */
public interface PubSubService {
    void handle(String channel, String message);
}
