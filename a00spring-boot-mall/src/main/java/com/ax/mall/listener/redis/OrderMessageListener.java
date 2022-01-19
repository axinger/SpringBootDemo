package com.ax.mall.listener.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * redis 消息的监听器
 * 获取redis 中的消息并进行处理
 */
@Component
public class OrderMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("缓存失效getChannel = " + new String(message.getChannel()));
        System.out.println("getBody = " + new String(message.getBody()));

    }
}
