package com.njucm.rabbitmq.topic;

import com.njucm.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 第三个参数 持久化
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
        String message = "新增商品 ：id=1001";
        // 持久化
        channel.basicPublish(EXCHANGE_NAME, "item.insert", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [商品服务:] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
