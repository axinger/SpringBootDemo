package com.ax.rabbitmq.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
@Slf4j
public class RabbitMqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Bean
    /**
     * 序列化
     * */
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * PostConstruct 构造器创建完成后注入
     */
    @PostConstruct
    public void init() {

        /**
         * correlationData 相关配置
         * ack 是否成功收到消息
         * cause 失败原因
         * */
        rabbitTemplate.setConfirmCallback(this);

        /**
         * 回退模式
         * 发送给交换机后, 路由到queue失败,才会执行
         * 处理消息模式
         *  1.丢弃,默认
         *  2.返回给发送方
         *
         *  事务方式 性能较差
         * */

        /**
         * 设置处理模式
         * */
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * broker收到消息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        Optional.ofNullable(correlationData).map(CorrelationData::getId).ifPresent((id) -> {
            System.out.println("mq 唯一id = " + id);
        });

//        final String id = Optional.ofNullable(correlationData).map(CorrelationData::getId).orElse("");


        log.info("correlationData -->" + correlationData);
        if (ack) {
            log.info("交换机收到消息");
        } else {
            log.info("交换机没有收到消息 cause=" + cause);
        }
    }

    /**
     * 消费者回退消息,消费者未接收到触发
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消费者回退消息: ----replyCode=" + replyCode + " replyText=" + replyText + " ");
    }
}
